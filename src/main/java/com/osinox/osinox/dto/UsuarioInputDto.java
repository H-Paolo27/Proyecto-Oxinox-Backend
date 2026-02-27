package com.osinox.osinox.dto;
import lombok.*;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInputDto {
    private String username;
    private String password;
    private Boolean activo;
    private Long personaId;
    private Set<Long> roleIds;
}
