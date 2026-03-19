package com.osinox.osinox.mapper;

import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.dto.TrabajadorDto;

public class TrabajadorMapper {

    public static TrabajadorDto toDto(Trabajador t) {
        if (t == null) return null;
        Long personaId = t.getPersona() == null ? null : t.getPersona().getId();
        String nombreCompleto = t.getPersona() == null ? null : t.getPersona().getNombreRazonSocial();

        return new TrabajadorDto(
            t.getId(),
            personaId,
            nombreCompleto,
            t.getCargo(),
            t.getTipoPension() == null ? null : t.getTipoPension().name(),
            t.getNombreAfp(),
            t.getFechaContratacion(),
            t.getFechaCese(),
            t.getSalario(),
            t.getEstado() == null ? null : t.getEstado().name()
        );
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
        t.setFechaCese(d.getFechaCese());
        t.setSalario(d.getSalario());
        if (d.getTipoPension() != null)
            t.setTipoPension(Trabajador.TipoPension.valueOf(d.getTipoPension()));
        t.setNombreAfp(d.getNombreAfp());
        if (d.getEstado() != null)
            t.setEstado(Trabajador.EstadoTrabajador.valueOf(d.getEstado()));
        return t;
    }
}