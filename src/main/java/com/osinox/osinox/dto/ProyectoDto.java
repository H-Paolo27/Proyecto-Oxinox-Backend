// ─────────────────────────────────────────────────────────────
// ProyectoDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDto {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Long clienteId;
    private String clienteNombre;
    private Long responsableId;
    private String responsableNombre;
    private String tipo;
    private String estado;
    private String prioridad;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    private BigDecimal presupuesto;
    private BigDecimal costoReal;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}