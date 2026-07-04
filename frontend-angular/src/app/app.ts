import { AfterViewInit, Component, ElementRef, signal, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import * as L from 'leaflet';

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
  productoNombre?: string;
  nombreFinca?: string;
  agricultorNombre?: string;
  precioSugerido?: number;
  moneda?: string;
  distanciaKm?: number;
  latitud?: number;
  longitud?: number;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements AfterViewInit {
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
  logLines = signal('Listo. Prueba la conexión o carga los datos de ejemplo para verificar que el mapa pinta correctamente.');

  private map!: L.Map;
  private searchMarker: L.CircleMarker | null = null;
  private ofertaMarkers: L.Marker[] = [];

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

  private limpiarMarkers(): void {
    this.ofertaMarkers.forEach((m) => this.map.removeLayer(m));
    this.ofertaMarkers = [];
  }

  private pintarPuntoBusqueda(lat: number, lng: number): void {
    if (this.searchMarker) this.map.removeLayer(this.searchMarker);
    this.searchMarker = L.circleMarker([lat, lng], { radius: 8, color: '#1976d2', fillColor: '#1976d2', fillOpacity: 0.9 })
      .addTo(this.map)
      .bindPopup('Punto de búsqueda');
    this.map.setView([lat, lng], 8);
  }

  private pintarOfertas(ofertas: OfertaMapa[]): void {
    this.limpiarMarkers();
    ofertas.forEach((o) => {
      if (o.latitud == null || o.longitud == null) return;
      const marker = L.marker([o.latitud, o.longitud]).addTo(this.map);
      const distancia = o.distanciaKm != null ? `${o.distanciaKm.toFixed(1)} km` : 'N/D';
      marker.bindPopup(`
        <b>${o.productoNombre || 'Producto'}</b><br>
        Finca: ${o.nombreFinca || '-'}<br>
        Agricultor: ${o.agricultorNombre || '-'}<br>
        Precio: ${o.precioSugerido ?? '-'} ${o.moneda || ''}<br>
        Distancia: ${distancia}
      `);
      this.ofertaMarkers.push(marker);
    });
    if (ofertas.length) {
      const group = L.featureGroup(this.searchMarker ? [...this.ofertaMarkers, this.searchMarker] : this.ofertaMarkers);
      this.map.fitBounds(group.getBounds().pad(0.2));
    }
  }
}
