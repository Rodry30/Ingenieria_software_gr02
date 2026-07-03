package com.foodgest.marketplace.ofertas.repositories;

import java.math.BigDecimal;
import java.util.UUID;

public interface OfertaMapaProjection {
    UUID getId();
    UUID getAgricultorId();
    UUID getProductoId();
    String getProductoNombre();
    String getAgricultorNombre();
    String getNombreFinca();
    String getVariedad();
    String getCalidad();
    BigDecimal getCantidadDisponible();
    String getUnidadMedida();
    BigDecimal getPrecioSugerido();
    String getMoneda();
    BigDecimal getLatitud();
    BigDecimal getLongitud();
    String getDireccionReferencia();
    Integer getRadioEntregaKm();
    String getCondicionEntrega();
    Boolean getAceptaNegociacion();
    Boolean getTieneSenasa();
    Boolean getTieneGlobalgap();
    Boolean getEsOrganico();
    Boolean getDestacada();
    Double getDistanciaKm();
}
