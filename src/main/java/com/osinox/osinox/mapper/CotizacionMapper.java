package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.CotizacionDto;
import com.osinox.osinox.entity.Cotizacion;
public class CotizacionMapper {
    public static CotizacionDto toDto(Cotizacion c) {
        if (c == null) return null;
        Long clienteId = c.getCliente() == null ? null : c.getCliente().getId();
        String clienteNombre = c.getCliente() == null ? null : c.getCliente().getNombreRazonSocial();
        Long vendedorId = c.getVendedor() == null ? null : c.getVendedor().getId();
        String vendedorNombre = c.getVendedor() == null ? null : c.getVendedor().getPersona().getNombreRazonSocial();
        Long proyectoId = c.getProyecto() == null ? null : c.getProyecto().getId();
        String proyectoNombre = c.getProyecto() == null ? null : c.getProyecto().getNombre();
        String estado = c.getEstado() == null ? null : c.getEstado().name();
        String moneda = c.getMoneda() == null ? null : c.getMoneda().name();
        return new CotizacionDto(
            c.getId(),
            c.getCodigo(),
            clienteId,
            clienteNombre,
            vendedorId,
            vendedorNombre,
            proyectoId,
            proyectoNombre,
            estado,
            moneda,
            c.getTipoCambio(),
            c.getSubtotal(),
            c.getIgv(),
            c.getTotal(),
            c.getCondicionesPago(),
            c.getTiempoEntrega(),
            c.getValidezDias(),
            c.getObservaciones(),
            c.getPdfUrl(),
            c.getFechaEmision(),
            c.getFechaVencimiento(),
            c.getFechaRegistro(),
            null // detalles null for now
        );
    }
    public static Cotizacion toEntity(CotizacionDto d) {
        if (d == null) return null;
        Cotizacion c = new Cotizacion();
        c.setId(d.getId());
        c.setCodigo(d.getCodigo());
        if (d.getClienteId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getClienteId());
            c.setCliente(p);
        }
        if (d.getVendedorId() != null) {
            com.osinox.osinox.entity.Trabajador t = new com.osinox.osinox.entity.Trabajador();
            t.setId(d.getVendedorId());
            c.setVendedor(t);
        }
        if (d.getProyectoId() != null) {
            com.osinox.osinox.entity.Proyecto proj = new com.osinox.osinox.entity.Proyecto();
            proj.setId(d.getProyectoId());
            c.setProyecto(proj);
        }
        try {
            if (d.getEstado() != null) c.setEstado(Cotizacion.EstadoCotizacion.valueOf(d.getEstado()));
        } catch (IllegalArgumentException ex) {}
        try {
            if (d.getMoneda() != null) c.setMoneda(Cotizacion.Moneda.valueOf(d.getMoneda()));
        } catch (IllegalArgumentException ex) {}
        c.setTipoCambio(d.getTipoCambio());
        c.setSubtotal(d.getSubtotal());
        c.setIgv(d.getIgv());
        c.setTotal(d.getTotal());
        c.setCondicionesPago(d.getCondicionesPago());
        c.setTiempoEntrega(d.getTiempoEntrega());
        c.setValidezDias(d.getValidezDias());
        c.setObservaciones(d.getObservaciones());
        c.setPdfUrl(d.getPdfUrl());
        c.setFechaEmision(d.getFechaEmision());
        c.setFechaVencimiento(d.getFechaVencimiento());
        c.setFechaRegistro(d.getFechaRegistro());
        return c;
    }
}
