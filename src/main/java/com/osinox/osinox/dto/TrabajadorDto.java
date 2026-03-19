// ─────────────────────────────────────────────────────────────
// TrabajadorDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorDto {
    private Long id;
    private Long personaId;
    private String nombreCompleto;        // de persona.nombreRazonSocial
    private String cargo;
    private String tipoPension;
    private String nombreAfp;
    private LocalDate fechaContratacion;
    private LocalDate fechaCese;
    private BigDecimal salario;
    private String estado;
}
 