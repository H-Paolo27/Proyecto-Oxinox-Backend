package com.osinox.osinox.dto;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorDto {
    private Long id;
    private Long personaId;
    private String cargo;
    private LocalDate fechaContratacion;
    private BigDecimal salario;
}
