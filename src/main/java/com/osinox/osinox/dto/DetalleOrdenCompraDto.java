// ─────────────────────────────────────────────────────────────
// DetalleOrdenCompraDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrdenCompraDto {
    private Long id;
    private Long productoId;
    private String descripcion;
    private BigDecimal cantidad;
    private String unidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private BigDecimal recibido;
}