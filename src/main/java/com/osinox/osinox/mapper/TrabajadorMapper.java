package com.osinox.osinox.mapper;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.dto.TrabajadorDto;
public class TrabajadorMapper {
    public static TrabajadorDto toDto(Trabajador t) {
        if (t == null) return null;
        Long personaId = t.getPersona() == null ? null : t.getPersona().getId();
        return new TrabajadorDto(t.getId(), personaId, t.getCargo(), t.getFechaContratacion(), t.getSalario());
    }
    public static Trabajador toEntity(TrabajadorDto d) {
        if (d == null) return null;
        Trabajador t = new Trabajador();
        t.setId(d.getId());
        if (d.getPersonaId() != null) {
            Persona p = new Persona();
            p.setId(d.getPersonaId());
            t.setPersona(p);
        }
        t.setCargo(d.getCargo());
        t.setFechaContratacion(d.getFechaContratacion());
        t.setSalario(d.getSalario());
        return t;
    }
}
