// ─────────────────────────────────────────────────────────────
// DetalleGuiaRemisionDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGuiaRemisionDto {
    private Long id;
    private Long productoId;
    private String codigo;
    private String descripcion;
    private String unidadMedida;
    private BigDecimal cantidad;
    private BigDecimal pesoTotal;
}
 