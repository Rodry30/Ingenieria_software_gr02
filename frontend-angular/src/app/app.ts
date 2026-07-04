import { AfterViewInit, Component, ElementRef, OnDestroy, signal, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import * as L from 'leaflet';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

// IconDefault._getIconUrl antepone un "imagePath" autodetectado desde el CSS
// (que el bundler de Angular resuelve a /media/...), ignorando las URLs de
// abajo. Se borra para caer al _getIconUrl base, que usa la URL tal cual.
delete (L.Icon.Default.prototype as unknown as { _getIconUrl?: unknown })._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'leaflet/marker-icon-2x.png',
  iconUrl: 'leaflet/marker-icon.png',
  shadowUrl: 'leaflet/marker-shadow.png',
});

interface OfertaMapa {
  id?: string;
  productoNombre?: string;
  nombreFinca?: string;
  agricultorNombre?: string;
  precioSugerido?: number;
  moneda?: string;
  distanciaKm?: number;
  latitud?: number;
  longitud?: number;
  estado?: string;
}

interface OfertaEvento extends OfertaMapa {
  tipoEvento: 'creada' | 'actualizada' | 'eliminada';
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements AfterViewInit, OnDestroy {
  private readonly mapContainer = viewChild.required<ElementRef<HTMLDivElement>>('mapContainer');

  apiBase = signal('http://localhost:8080');
  email = signal('');
  password = signal('');
  tokenManual = signal('');
  lat = signal('-12.0464');
  lng = signal('-77.0428');
  radio = signal('50');

  token: string | null = null;
  backendOk = signal<boolean | null>(null);
  authOk = signal(false);
  wsOk = signal<boolean | null>(null);
  logLines = signal('Listo. Prueba la conexión o carga los datos de ejemplo para verificar que el mapa pinta correctamente.');

  private map!: L.Map;
  private searchMarker: L.CircleMarker | null = null;
  private searchPoint: { lat: number; lng: number } | null = null;
  private ofertaMarkers = new Map<string, L.Marker>();
  private stompClient: Client | null = null;

  ngAfterViewInit(): void {
    this.map = L.map(this.mapContainer().nativeElement).setView([-12.0464, -77.0428], 6);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(this.map);

    this.map.on('click', (e: L.LeafletMouseEvent) => {
      this.lat.set(e.latlng.lat.toFixed(6));
      this.lng.set(e.latlng.lng.toFixed(6));
      this.log(`Punto seleccionado en el mapa: ${e.latlng.lat.toFixed(6)}, ${e.latlng.lng.toFixed(6)}`);
    });
  }

  ngOnDestroy(): void {
    this.desconectarWs();
  }

  private log(message: string, obj?: unknown): void {
    const time = new Date().toLocaleTimeString();
    let line = `[${time}] ${message}`;
    if (obj !== undefined) line += '\n' + JSON.stringify(obj, null, 2);
    this.logLines.set(line + '\n\n' + this.logLines());
  }

  private setToken(value: string | null): void {
    this.token = value && value.trim() ? value.trim() : null;
    this.authOk.set(!!this.token);
  }

  onTokenManualInput(value: string): void {
    this.tokenManual.set(value);
    this.setToken(value);
  }

  async checkBackend(): Promise<void> {
    try {
      const res = await fetch(`${this.apiBase().replace(/\/$/, '')}/v3/api-docs`);
      this.backendOk.set(res.ok);
      this.log('Chequeo de backend', { status: res.status });
    } catch (err) {
      this.backendOk.set(false);
      this.log('Error al conectar con el backend: ' + (err as Error).message);
    }
  }

  async login(): Promise<void> {
    try {
      const res = await fetch(`${this.apiBase().replace(/\/$/, '')}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: this.email(), password: this.password() }),
      });
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || `HTTP ${res.status}`);
      const t = data.data ? data.data.accessToken || data.data.token : data.accessToken || data.token;
      if (!t) throw new Error('La respuesta no trajo ningun token');
      this.tokenManual.set(t);
      this.setToken(t);
      this.log('Login exitoso', data);
    } catch (err) {
      this.authOk.set(false);
      this.log('Error en login: ' + (err as Error).message);
    }
  }

  async buscarOfertas(): Promise<void> {
    const lat = this.lat();
    const lng = this.lng();
    const radio = this.radio();
    this.pintarPuntoBusqueda(parseFloat(lat), parseFloat(lng));

    const headers: Record<string, string> = {};
    if (this.token) headers['Authorization'] = 'Bearer ' + this.token;

    try {
      const url = `${this.apiBase().replace(/\/$/, '')}/api/marketplace/ofertas/mapa?latitud=${lat}&longitud=${lng}&radioKm=${radio}`;
      const res = await fetch(url, { headers });
      const data = await res.json();
      if (!res.ok) throw new Error((data && data.message) || `HTTP ${res.status}`);
      const ofertas: OfertaMapa[] = Array.isArray(data) ? data : data.data || [];
      this.pintarOfertas(ofertas);
      this.log(`Ofertas encontradas: ${ofertas.length}`, ofertas);
    } catch (err) {
      this.log('Error al buscar ofertas: ' + (err as Error).message);
    }
  }

  cargarDemo(): void {
    const lat = parseFloat(this.lat());
    const lng = parseFloat(this.lng());
    this.pintarPuntoBusqueda(lat, lng);
    const demo: OfertaMapa[] = [
      { latitud: lat + 0.15, longitud: lng + 0.1, productoNombre: 'Papa amarilla', nombreFinca: 'Finca El Sol', agricultorNombre: 'Juan Perez', precioSugerido: 2.5, moneda: 'PEN', distanciaKm: 12.4 },
      { latitud: lat - 0.2, longitud: lng + 0.25, productoNombre: 'Maiz choclo', nombreFinca: 'Finca Verde', agricultorNombre: 'Maria Lopez', precioSugerido: 3.1, moneda: 'PEN', distanciaKm: 28.9 },
      { latitud: lat + 0.3, longitud: lng - 0.3, productoNombre: 'Palta Hass', nombreFinca: 'Finca Los Andes', agricultorNombre: 'Carlos Ruiz', precioSugerido: 6.8, moneda: 'PEN', distanciaKm: 41.2 },
    ];
    this.pintarOfertas(demo);
    this.log('Datos de ejemplo cargados (no requieren backend)', demo);
  }

  conectarWs(): void {
    const base = this.apiBase().replace(/\/$/, '');
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(`${base}/ws`) as unknown as WebSocket,
      onConnect: () => {
        this.wsOk.set(true);
        this.log('WebSocket conectado a /topic/ofertas');
        this.stompClient!.subscribe('/topic/ofertas', (frame) => {
          this.manejarEventoOferta(JSON.parse(frame.body));
        });
      },
      onStompError: (frame) => {
        this.wsOk.set(false);
        this.log('Error de WebSocket (STOMP): ' + frame.headers['message']);
      },
      onWebSocketError: (err) => {
        this.wsOk.set(false);
        this.log('Error de WebSocket: ' + err);
      },
    });
    this.stompClient.activate();
  }

  desconectarWs(): void {
    this.stompClient?.deactivate();
    this.stompClient = null;
    this.wsOk.set(false);
  }

  private manejarEventoOferta(o: OfertaEvento): void {
    this.log(`Evento en vivo: oferta ${o.tipoEvento}`, o);

    if (o.tipoEvento === 'eliminada' || o.estado !== 'activa') {
      const existente = o.id ? this.ofertaMarkers.get(o.id) : undefined;
      if (existente) {
        this.map.removeLayer(existente);
        this.ofertaMarkers.delete(o.id!);
      }
      return;
    }
    if (o.latitud == null || o.longitud == null || !o.id) return;

    if (this.searchPoint) {
      const radioKm = parseFloat(this.radio() || '50');
      const d = this.distanciaHaversineKm(this.searchPoint.lat, this.searchPoint.lng, o.latitud, o.longitud);
      if (d > radioKm) return; // fuera del radio de la ultima busqueda
    }

    const existente = this.ofertaMarkers.get(o.id);
    if (existente) this.map.removeLayer(existente);
    const marker = L.marker([o.latitud, o.longitud]).addTo(this.map);
    marker.bindPopup(this.popupOferta(o, 'actualizado en vivo'));
    this.ofertaMarkers.set(o.id, marker);
  }

  private distanciaHaversineKm(lat1: number, lon1: number, lat2: number, lon2: number): number {
    const R = 6371;
    const dLat = ((lat2 - lat1) * Math.PI) / 180;
    const dLon = ((lon2 - lon1) * Math.PI) / 180;
    const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos((lat1 * Math.PI) / 180) * Math.cos((lat2 * Math.PI) / 180) * Math.sin(dLon / 2) ** 2;
    return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  }

  private limpiarMarkers(): void {
    this.ofertaMarkers.forEach((m) => this.map.removeLayer(m));
    this.ofertaMarkers.clear();
  }

  private pintarPuntoBusqueda(lat: number, lng: number): void {
    this.searchPoint = { lat, lng };
    if (this.searchMarker) this.map.removeLayer(this.searchMarker);
    this.searchMarker = L.circleMarker([lat, lng], { radius: 8, color: '#1976d2', fillColor: '#1976d2', fillOpacity: 0.9 })
      .addTo(this.map)
      .bindPopup('Punto de búsqueda');
    this.map.setView([lat, lng], 8);
  }

  private popupOferta(o: OfertaMapa, extra?: string): string {
    const distancia = o.distanciaKm != null ? `${o.distanciaKm.toFixed(1)} km` : 'N/D';
    return `
        <b>${o.productoNombre || 'Producto'}</b><br>
        Finca: ${o.nombreFinca || '-'}<br>
        Agricultor: ${o.agricultorNombre || '-'}<br>
        Precio: ${o.precioSugerido ?? '-'} ${o.moneda || ''}<br>
        Distancia: ${distancia}
        ${extra ? '<br><i>' + extra + '</i>' : ''}
      `;
  }

  private pintarOfertas(ofertas: OfertaMapa[]): void {
    this.limpiarMarkers();
    ofertas.forEach((o, i) => {
      if (o.latitud == null || o.longitud == null) return;
      const marker = L.marker([o.latitud, o.longitud]).addTo(this.map);
      marker.bindPopup(this.popupOferta(o));
      this.ofertaMarkers.set(o.id || `demo-${i}`, marker);
    });
    if (ofertas.length) {
      const group = L.featureGroup(this.searchMarker ? [...this.ofertaMarkers.values(), this.searchMarker] : [...this.ofertaMarkers.values()]);
      this.map.fitBounds(group.getBounds().pad(0.2));
    }
  }
}
