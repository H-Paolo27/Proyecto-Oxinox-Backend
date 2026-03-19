// ─────────────────────────────────────────────────────────────
// DetalleCotizacionDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCotizacionDto {
    private Long id;
    private Long productoId;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;
}
 