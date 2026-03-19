package com.osinox.osinox.dto;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long id;
    private String username;
    private String emailAcceso;
    private Boolean activo;
    private Long personaId;
    private String nombreCompleto;        // de persona.nombreRazonSocial
    private Set<String> roles;            // nombres de roles
}
