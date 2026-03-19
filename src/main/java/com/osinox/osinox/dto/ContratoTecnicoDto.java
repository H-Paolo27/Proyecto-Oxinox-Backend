// ─────────────────────────────────────────────────────────────
// ContratoTecnicoDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoTecnicoDto {
    private Long id;
    private String codigo;
    private Long clienteId;
    private String clienteNombre;
    private Long proyectoId;
    private String proyectoNombre;
    private String tipo;
    private String descripcion;
    private BigDecimal monto;
    private String moneda;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String archivoUrl;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}