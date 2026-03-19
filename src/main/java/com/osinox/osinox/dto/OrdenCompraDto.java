// ─────────────────────────────────────────────────────────────
// OrdenCompraDto.java
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
public class OrdenCompraDto {
    private Long id;
    private String codigo;
    private Long proveedorId;
    private String proveedorNombre;
    private Long solicitanteId;
    private String solicitanteNombre;
    private String estado;
    private String moneda;
    private BigDecimal tipoCambio;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private LocalDate fechaEmision;
    private LocalDate fechaEntregaEstimada;
    private LocalDate fechaEntregaReal;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    private List<DetalleOrdenCompraDto> detalles;
}
 