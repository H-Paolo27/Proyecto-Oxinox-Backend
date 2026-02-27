package com.osinox.osinox.mapper;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.dto.PersonaDto;
public class PersonaMapper {
    public static PersonaDto toDto(Persona p) {
        if (p == null) return null;
        return new PersonaDto(
                p.getId(),
                p.getTipoPersona() == null ? null : p.getTipoPersona().name(),
                p.getNombreRazonSocial(),
                p.getTipoDocumento() == null ? null : p.getTipoDocumento().name(),
                p.getNumeroDocumento(),
                p.getTipoRelacion() == null ? null : p.getTipoRelacion().name(),
                p.getTelefono(),
                p.getEmail(),
                p.getDireccion(),
                p.getEstado(),
                p.getFechaRegistro()
        );
    }
    public static Persona toEntity(PersonaDto d) {
        if (d == null) return null;
        Persona p = new Persona();
        p.setId(d.getId());
        p.setNombreRazonSocial(d.getNombreRazonSocial());
        p.setNumeroDocumento(d.getNumeroDocumento());
        p.setTelefono(d.getTelefono());
        p.setEmail(d.getEmail());
        p.setDireccion(d.getDireccion());
        p.setEstado(d.getEstado());
        p.setFechaRegistro(d.getFechaRegistro());
        try {
            if (d.getTipoPersona() != null) p.setTipoPersona(Persona.TipoPersona.valueOf(d.getTipoPersona()));
        } catch (IllegalArgumentException ex) {
            // ignore invalid enum
        }
        try {
            if (d.getTipoDocumento() != null) p.setTipoDocumento(Persona.TipoDocumento.valueOf(d.getTipoDocumento()));
        } catch (IllegalArgumentException ex) {
            // ignore
        }
        try {
            if (d.getTipoRelacion() != null) p.setTipoRelacion(Persona.TipoRelacion.valueOf(d.getTipoRelacion()));
        } catch (IllegalArgumentException ex) {
            // ignore
        }
        return p;
    }
}
