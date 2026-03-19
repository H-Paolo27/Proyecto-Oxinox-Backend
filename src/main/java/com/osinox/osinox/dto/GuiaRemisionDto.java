// ─────────────────────────────────────────────────────────────
// GuiaRemisionDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaRemisionDto {
    private Long id;
    private String serie;
    private String correlativo;
    private Long remitenteId;
    private String remitenteNombre;
    private Long destinatarioId;
    private String destinatarioNombre;
    private Long proyectoId;
    private Long comprobanteId;
    private String rucEmisor;
    private String puntoPartida;
    private String puntoLlegada;
    private LocalDate fechaTraslado;
    private String motivoTraslado;
    private String modalidadTransporte;
    private String rucTransportista;
    private String nombreTransportista;
    private String numLicenciaConducir;
    private String numConstanciaInscripcion;
    private String marcaVehiculo;
    private String placaVehiculo;
    private String estado;
    private String pdfUrl;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    private List<DetalleGuiaRemisionDto> detalles;
}