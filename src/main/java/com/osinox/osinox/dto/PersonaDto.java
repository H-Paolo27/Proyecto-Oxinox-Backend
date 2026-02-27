package com.osinox.osinox.dto;
import lombok.*;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDto {
    private Long id;
    private String tipoPersona;
    private String nombreRazonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String tipoRelacion;
    private String telefono;
    private String email;
    private String direccion;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
}
