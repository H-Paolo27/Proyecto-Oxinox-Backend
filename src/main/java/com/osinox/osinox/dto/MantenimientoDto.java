// ─────────────────────────────────────────────────────────────
// MantenimientoDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDto {
    private Long id;
    private String codigo;
    private Long proyectoId;
    private String proyectoNombre;
    private Long clienteId;
    private String clienteNombre;
    private String tipo;
    private String descripcion;
    private Long tecnicoId;
    private String tecnicoNombre;
    private String estado;
    private LocalDate fechaProgramada;
    private LocalDate fechaRealizada;
    private BigDecimal costo;
    private String informeUrl;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}