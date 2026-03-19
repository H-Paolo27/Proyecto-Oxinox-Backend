// ─────────────────────────────────────────────────────────────
// OrdenProduccionMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.OrdenProduccion;
import com.osinox.osinox.entity.Proyecto;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.dto.OrdenProduccionDto;
 
public class OrdenProduccionMapper {
 
    public static OrdenProduccionDto toDto(OrdenProduccion o) {
        if (o == null) return null;
        Long proyectoId = o.getProyecto() == null ? null : o.getProyecto().getId();
        String proyectoNombre = o.getProyecto() == null ? null : o.getProyecto().getNombre();
        Long responsableId = o.getResponsable() == null ? null : o.getResponsable().getId();
        String responsableNombre = o.getResponsable() == null ? null :
                o.getResponsable().getPersona() == null ? null :
                o.getResponsable().getPersona().getNombreRazonSocial();
 
        return new OrdenProduccionDto(
            o.getId(),
            o.getCodigo(),
            proyectoId,
            proyectoNombre,
            o.getDescripcion(),
            o.getEstado() == null ? null : o.getEstado().name(),
            o.getPrioridad() == null ? null : o.getPrioridad().name(),
            responsableId,
            responsableNombre,
            o.getFechaInicio(),
            o.getFechaFinEstimada(),
            o.getFechaFinReal(),
            o.getObservaciones(),
            o.getFechaRegistro()
        );
    }
 
    public static OrdenProduccion toEntity(OrdenProduccionDto d) {
        if (d == null) return null;
        OrdenProduccion o = new OrdenProduccion();
        o.setId(d.getId());
        o.setCodigo(d.getCodigo());
        if (d.getProyectoId() != null) {
            Proyecto proyecto = new Proyecto();
            proyecto.setId(d.getProyectoId());
            o.setProyecto(proyecto);
        }
        if (d.getResponsableId() != null) {
            Trabajador responsable = new Trabajador();
            responsable.setId(d.getResponsableId());
            o.setResponsable(responsable);
        }
        o.setDescripcion(d.getDescripcion());
        if (d.getEstado() != null)
            o.setEstado(OrdenProduccion.EstadoOrden.valueOf(d.getEstado()));
        if (d.getPrioridad() != null)
            o.setPrioridad(OrdenProduccion.Prioridad.valueOf(d.getPrioridad()));
        o.setFechaInicio(d.getFechaInicio());
        o.setFechaFinEstimada(d.getFechaFinEstimada());
        o.setFechaFinReal(d.getFechaFinReal());
        o.setObservaciones(d.getObservaciones());
        return o;
    }
}