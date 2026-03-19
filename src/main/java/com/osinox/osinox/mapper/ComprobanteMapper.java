package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.ComprobanteDto;
import com.osinox.osinox.entity.Comprobante;
public class ComprobanteMapper {
    public static ComprobanteDto toDto(Comprobante c) {
        if (c == null) return null;
        Long personaId = c.getPersona() == null ? null : c.getPersona().getId();
        String personaNombre = c.getPersona() == null ? null : c.getPersona().getNombreRazonSocial();
        String personaNumeroDocumento = c.getPersona() == null ? null : c.getPersona().getNumeroDocumento();
        Long cotizacionId = c.getCotizacion() == null ? null : c.getCotizacion().getId();
        Long proyectoId = c.getProyecto() == null ? null : c.getProyecto().getId();
        String tipo = c.getTipo() == null ? null : c.getTipo().name();
        String moneda = c.getMoneda() == null ? null : c.getMoneda().name();
        String estado = c.getEstado() == null ? null : c.getEstado().name();
        return new ComprobanteDto(
            c.getId(),
            tipo,
            c.getSerie(),
            c.getCorrelativo(),
            personaId,
            personaNombre,
            personaNumeroDocumento,
            cotizacionId,
            proyectoId,
            c.getRucEmisor(),
            c.getSubtotal(),
            c.getIgv(),
            c.getTotal(),
            moneda,
            c.getTipoCambio(),
            estado,
            c.getXmlUrl(),
            c.getPdfUrl(),
            c.getFechaEmision(),
            c.getFechaVencimiento(),
            c.getObservaciones(),
            c.getFechaRegistro(),
            null // detalles require recursive mapping, set null for now
        );
    }
    public static Comprobante toEntity(ComprobanteDto d) {
        if (d == null) return null;
        Comprobante c = new Comprobante();
        c.setId(d.getId());
        try {
            if (d.getTipo() != null) c.setTipo(Comprobante.TipoComprobante.valueOf(d.getTipo()));
        } catch (IllegalArgumentException ex) {}
        c.setSerie(d.getSerie());
        c.setCorrelativo(d.getCorrelativo());
        if (d.getPersonaId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getPersonaId());
            c.setPersona(p);
        }
        if (d.getCotizacionId() != null) {
            com.osinox.osinox.entity.Cotizacion cot = new com.osinox.osinox.entity.Cotizacion();
            cot.setId(d.getCotizacionId());
            c.setCotizacion(cot);
        }
        if (d.getProyectoId() != null) {
            com.osinox.osinox.entity.Proyecto proj = new com.osinox.osinox.entity.Proyecto();
            proj.setId(d.getProyectoId());
            c.setProyecto(proj);
        }
        c.setRucEmisor(d.getRucEmisor());
        c.setSubtotal(d.getSubtotal());
        c.setIgv(d.getIgv());
        c.setTotal(d.getTotal());
        try {
            if (d.getMoneda() != null) c.setMoneda(Comprobante.Moneda.valueOf(d.getMoneda()));
        } catch (IllegalArgumentException ex) {}
        c.setTipoCambio(d.getTipoCambio());
        try {
            if (d.getEstado() != null) c.setEstado(Comprobante.EstadoComprobante.valueOf(d.getEstado()));
        } catch (IllegalArgumentException ex) {}
        c.setXmlUrl(d.getXmlUrl());
        c.setPdfUrl(d.getPdfUrl());
        c.setFechaEmision(d.getFechaEmision());
        c.setFechaVencimiento(d.getFechaVencimiento());
        c.setObservaciones(d.getObservaciones());
        c.setFechaRegistro(d.getFechaRegistro());
        return c;
    }
}
