// ─────────────────────────────────────────────────────────────
// ProyectoMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.Proyecto;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.dto.ProyectoDto;
 
public class ProyectoMapper {
 
    public static ProyectoDto toDto(Proyecto p) {
        if (p == null) return null;
        Long clienteId = p.getCliente() == null ? null : p.getCliente().getId();
        String clienteNombre = p.getCliente() == null ? null : p.getCliente().getNombreRazonSocial();
        Long responsableId = p.getResponsable() == null ? null : p.getResponsable().getId();
        String responsableNombre = p.getResponsable() == null ? null :
                p.getResponsable().getPersona() == null ? null :
                p.getResponsable().getPersona().getNombreRazonSocial();
 
        return new ProyectoDto(
            p.getId(),
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            clienteId,
            clienteNombre,
            responsableId,
            responsableNombre,
            p.getTipo() == null ? null : p.getTipo().name(),
            p.getEstado() == null ? null : p.getEstado().name(),
            p.getPrioridad() == null ? null : p.getPrioridad().name(),
            p.getFechaInicio(),
            p.getFechaFinEstimada(),
            p.getFechaFinReal(),
            p.getPresupuesto(),
            p.getCostoReal(),
            p.getObservaciones(),
            p.getFechaRegistro()
        );
    }
 
    public static Proyecto toEntity(ProyectoDto d) {
        if (d == null) return null;
        Proyecto p = new Proyecto();
        p.setId(d.getId());
        p.setCodigo(d.getCodigo());
        p.setNombre(d.getNombre());
        p.setDescripcion(d.getDescripcion());
        if (d.getClienteId() != null) {
            Persona cliente = new Persona();
            cliente.setId(d.getClienteId());
            p.setCliente(cliente);
        }
        if (d.getResponsableId() != null) {
            Trabajador responsable = new Trabajador();
            responsable.setId(d.getResponsableId());
            p.setResponsable(responsable);
        }
        if (d.getTipo() != null)
            p.setTipo(Proyecto.TipoProyecto.valueOf(d.getTipo()));
        if (d.getEstado() != null)
            p.setEstado(Proyecto.EstadoProyecto.valueOf(d.getEstado()));
        if (d.getPrioridad() != null)
            p.setPrioridad(Proyecto.Prioridad.valueOf(d.getPrioridad()));
        p.setFechaInicio(d.getFechaInicio());
        p.setFechaFinEstimada(d.getFechaFinEstimada());
        p.setFechaFinReal(d.getFechaFinReal());
        p.setPresupuesto(d.getPresupuesto());
        p.setCostoReal(d.getCostoReal());
        p.setObservaciones(d.getObservaciones());
        return p;
    }
}