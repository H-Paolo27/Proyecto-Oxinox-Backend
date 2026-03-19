// ─────────────────────────────────────────────────────────────
// ComprobanteDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDto {
    private Long id;
    private String tipo;
    private String serie;
    private String correlativo;
    private Long personaId;
    private String personaNombre;
    private String personaNumeroDocumento;
    private Long cotizacionId;
    private Long proyectoId;
    private String rucEmisor;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String moneda;
    private BigDecimal tipoCambio;
    private String estado;
    private String xmlUrl;
    private String pdfUrl;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    private List<DetalleComprobanteDto> detalles;
}