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

	    Long personaId = u.getPersona() == null ? null : u.getPersona().getId();
	    String nombreCompleto = u.getPersona() == null ? null : u.getPersona().getNombreRazonSocial();

	    Set<String> roles = null;
	    if (u.getRoles() != null) {
	        roles = u.getRoles().stream()
	                .map(Rol::getNombre)          // <- nombres, no IDs
	                .collect(Collectors.toSet());
	    }

	    return new UsuarioDto(
	        u.getId(),
	        u.getUsername(),
	        u.getEmailAcceso(),
	        u.getActivo(),
	        personaId,
	        nombreCompleto,
	        roles
	    );
	}
	public static Usuario toEntity(UsuarioInputDto d) {
	    if (d == null) return null;
	    Usuario u = new Usuario();
	    u.setUsername(d.getUsername());
	    u.setEmailAcceso(d.getEmailAcceso());  // <- nuevo
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
