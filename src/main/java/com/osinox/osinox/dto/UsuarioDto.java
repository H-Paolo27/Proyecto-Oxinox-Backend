package com.osinox.osinox.dto;
import lombok.*;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long id;
    private String username;
    private Boolean activo;
    private Long personaId;
    private Set<Long> roleIds;
}
