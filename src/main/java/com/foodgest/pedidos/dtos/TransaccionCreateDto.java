package com.foodgest.pedidos.dtos;

import jakarta.validation.constraints.NotBlank;

public class TransaccionCreateDto {

    @NotBlank(message = "La pasarela de pago es obligatoria")
    private String pasarelaPago;

    @NotBlank(message = "La referencia externa es obligatoria")
    private String referenciaExterna;

    private String codigoAutorizacion;
    private String metadata = "{}";

    public String getPasarelaPago() { return pasarelaPago; }
    public void setPasarelaPago(String pasarelaPago) { this.pasarelaPago = pasarelaPago; }
    public String getReferenciaExterna() { return referenciaExterna; }
    public void setReferenciaExterna(String referenciaExterna) { this.referenciaExterna = referenciaExterna; }
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
    public void setCodigoAutorizacion(String codigoAutorizacion) { this.codigoAutorizacion = codigoAutorizacion; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}

