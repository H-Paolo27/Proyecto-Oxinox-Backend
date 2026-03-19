package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.MantenimientoDto;
import com.osinox.osinox.entity.Mantenimiento;
public class MantenimientoMapper {
    public static MantenimientoDto toDto(Mantenimiento m) {
        if (m == null) return null;
        Long proyectoId = m.getProyecto() == null ? null : m.getProyecto().getId();
        String proyectoNombre = m.getProyecto() == null ? null : m.getProyecto().getNombre();
        Long clienteId = m.getCliente() == null ? null : m.getCliente().getId();
        String clienteNombre = m.getCliente() == null ? null : m.getCliente().getNombreRazonSocial();
        Long tecnicoId = m.getTecnico() == null ? null : m.getTecnico().getId();
        String tecnicoNombre = m.getTecnico() == null ? null : m.getTecnico().getPersona().getNombreRazonSocial();
        String tipo = m.getTipo() == null ? null : m.getTipo().name();
        String estado = m.getEstado() == null ? null : m.getEstado().name();
        return new MantenimientoDto(
            m.getId(),
            m.getCodigo(),
            proyectoId,
            proyectoNombre,
            clienteId,
            clienteNombre,
            tipo,
            m.getDescripcion(),
            tecnicoId,
            tecnicoNombre,
            estado,
            m.getFechaProgramada(),
            m.getFechaRealizada(),
            m.getCosto(),
            m.getInformeUrl(),
            m.getObservaciones(),
            m.getFechaRegistro()
        );
    }
    public static Mantenimiento toEntity(MantenimientoDto d) {
        if (d == null) return null;
        Mantenimiento m = new Mantenimiento();
        m.setId(d.getId());
        m.setCodigo(d.getCodigo());
        if (d.getProyectoId() != null) {
            com.osinox.osinox.entity.Proyecto p = new com.osinox.osinox.entity.Proyecto();
            p.setId(d.getProyectoId());
            m.setProyecto(p);
        }
        if (d.getClienteId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getClienteId());
            m.setCliente(p);
        }
        if (d.getTecnicoId() != null) {
            com.osinox.osinox.entity.Trabajador t = new com.osinox.osinox.entity.Trabajador();
            t.setId(d.getTecnicoId());
            m.setTecnico(t);
        }
        try {
            if (d.getTipo() != null) m.setTipo(Mantenimiento.TipoMantenimiento.valueOf(d.getTipo()));
        } catch (IllegalArgumentException ex) {}
        m.setDescripcion(d.getDescripcion());
        try {
            if (d.getEstado() != null) m.setEstado(Mantenimiento.EstadoMantenimiento.valueOf(d.getEstado()));
        } catch (IllegalArgumentException ex) {}
        m.setFechaProgramada(d.getFechaProgramada());
        m.setFechaRealizada(d.getFechaRealizada());
        m.setCosto(d.getCosto());
        m.setInformeUrl(d.getInformeUrl());
        m.setObservaciones(d.getObservaciones());
        m.setFechaRegistro(d.getFechaRegistro());
        return m;
    }
}
