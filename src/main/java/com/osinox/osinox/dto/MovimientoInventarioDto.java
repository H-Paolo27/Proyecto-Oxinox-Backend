// ─────────────────────────────────────────────────────────────
// MovimientoInventarioDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventarioDto {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private String tipo;
    private BigDecimal cantidad;
    private BigDecimal stockAnterior;
    private BigDecimal stockPosterior;
    private String motivo;
    private String referenciaTipo;
    private Long referenciaId;
    private Long usuarioId;
    private String usuarioUsername;
    private LocalDateTime fecha;
}