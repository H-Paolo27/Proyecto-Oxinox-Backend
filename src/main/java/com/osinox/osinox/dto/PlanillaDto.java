// ─────────────────────────────────────────────────────────────
// PlanillaDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaDto {
    private Long id;
    private String periodo;
    private Long trabajadorId;
    private String trabajadorNombre;
    private BigDecimal sueldoBase;
    private Integer diasTrabajados;
    private BigDecimal horasExtra;
    private BigDecimal montoHorasExtra;
    private BigDecimal gratificacion;
    private BigDecimal cts;
    private BigDecimal asignacionFamiliar;
    private BigDecimal essalud;
    private String tipoPension;
    private BigDecimal descuentoPension;
    private BigDecimal rentaQuinta;
    private BigDecimal otrosDescuentos;
    private BigDecimal totalBruto;
    private BigDecimal totalDescuentos;
    private BigDecimal totalNeto;
    private String estado;
    private LocalDate fechaPago;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}