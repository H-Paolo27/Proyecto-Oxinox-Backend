// ─────────────────────────────────────────────────────────────
// CotizacionDto.java
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
public class CotizacionDto {
    private Long id;
    private String codigo;
    private Long clienteId;
    private String clienteNombre;
    private Long vendedorId;
    private String vendedorNombre;
    private Long proyectoId;
    private String proyectoNombre;
    private String estado;
    private String moneda;
    private BigDecimal tipoCambio;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String condicionesPago;
    private String tiempoEntrega;
    private Integer validezDias;
    private String observaciones;
    private String pdfUrl;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaRegistro;
    private List<DetalleCotizacionDto> detalles;
}