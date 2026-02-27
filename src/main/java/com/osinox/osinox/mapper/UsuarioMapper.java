package com.osinox.osinox.mapper;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.dto.UsuarioDto;
import com.osinox.osinox.dto.UsuarioInputDto;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
public class UsuarioMapper {
    public static UsuarioDto toDto(Usuario u) {
        if (u == null) return null;
        Set<Long> roleIds = null;
        if (u.getRoles() != null) {
            roleIds = u.getRoles().stream().map(Rol::getId).collect(Collectors.toSet());
        }
        Long personaId = u.getPersona() == null ? null : u.getPersona().getId();
        return new UsuarioDto(u.getId(), u.getUsername(), u.getActivo(), personaId, roleIds);
    }
    public static Usuario toEntity(UsuarioInputDto d) {
        if (d == null) return null;
        Usuario u = new Usuario();
        u.setUsername(d.getUsername());
        u.setPassword(d.getPassword());
        u.setActivo(d.getActivo() == null ? Boolean.TRUE : d.getActivo());
        if (d.getPersonaId() != null) {
            Persona p = new Persona();
            p.setId(d.getPersonaId());
            u.setPersona(p);
        }
        if (d.getRoleIds() != null) {
            Set<Rol> roles = new HashSet<>();
            for (Long rid : d.getRoleIds()) {
                Rol r = new Rol();
                r.setId(rid);
                roles.add(r);
            }
            u.setRoles(roles);
        }
        return u;
    }
}
