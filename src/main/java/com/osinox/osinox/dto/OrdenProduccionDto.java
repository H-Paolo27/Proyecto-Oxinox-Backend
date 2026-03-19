// ─────────────────────────────────────────────────────────────
// OrdenProduccionDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccionDto {
    private Long id;
    private String codigo;
    private Long proyectoId;
    private String proyectoNombre;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Long responsableId;
    private String responsableNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}