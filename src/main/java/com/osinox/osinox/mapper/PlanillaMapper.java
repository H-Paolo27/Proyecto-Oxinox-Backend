// ─────────────────────────────────────────────────────────────
// PlanillaMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.Planilla;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.dto.PlanillaDto;
 
public class PlanillaMapper {
 
    public static PlanillaDto toDto(Planilla p) {
        if (p == null) return null;
        Long trabajadorId = p.getTrabajador() == null ? null : p.getTrabajador().getId();
        String trabajadorNombre = p.getTrabajador() == null ? null :
                p.getTrabajador().getPersona() == null ? null :
                p.getTrabajador().getPersona().getNombreRazonSocial();
 
        return new PlanillaDto(
            p.getId(),
            p.getPeriodo(),
            trabajadorId,
            trabajadorNombre,
            p.getSueldoBase(),
            p.getDiasTrabajados(),
            p.getHorasExtra(),
            p.getMontoHorasExtra(),
            p.getGratificacion(),
            p.getCts(),
            p.getAsignacionFamiliar(),
            p.getEssalud(),
            p.getTipoPension() == null ? null : p.getTipoPension().name(),
            p.getDescuentoPension(),
            p.getRentaQuinta(),
            p.getOtrosDescuentos(),
            p.getTotalBruto(),
            p.getTotalDescuentos(),
            p.getTotalNeto(),
            p.getEstado() == null ? null : p.getEstado().name(),
            p.getFechaPago(),
            p.getObservaciones(),
            p.getFechaRegistro()
        );
    }
 
    public static Planilla toEntity(PlanillaDto d) {
        if (d == null) return null;
        Planilla p = new Planilla();
        p.setId(d.getId());
        p.setPeriodo(d.getPeriodo());
        if (d.getTrabajadorId() != null) {
            Trabajador t = new Trabajador();
            t.setId(d.getTrabajadorId());
            p.setTrabajador(t);
        }
        p.setSueldoBase(d.getSueldoBase());
        p.setDiasTrabajados(d.getDiasTrabajados());
        p.setHorasExtra(d.getHorasExtra());
        p.setMontoHorasExtra(d.getMontoHorasExtra());
        p.setGratificacion(d.getGratificacion());
        p.setCts(d.getCts());
        p.setAsignacionFamiliar(d.getAsignacionFamiliar());
        p.setEssalud(d.getEssalud());
        if (d.getTipoPension() != null)
            p.setTipoPension(Planilla.TipoPension.valueOf(d.getTipoPension()));
        p.setDescuentoPension(d.getDescuentoPension());
        p.setRentaQuinta(d.getRentaQuinta());
        p.setOtrosDescuentos(d.getOtrosDescuentos());
        p.setTotalBruto(d.getTotalBruto());
        p.setTotalDescuentos(d.getTotalDescuentos());
        p.setTotalNeto(d.getTotalNeto());
        if (d.getEstado() != null)
            p.setEstado(Planilla.EstadoPlanilla.valueOf(d.getEstado()));
        p.setFechaPago(d.getFechaPago());
        p.setObservaciones(d.getObservaciones());
        return p;
    }
}
 