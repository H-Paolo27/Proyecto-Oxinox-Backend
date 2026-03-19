// ─────────────────────────────────────────────────────────────
// OrdenCompraMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.OrdenCompra;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.dto.OrdenCompraDto;
import java.util.stream.Collectors;
 
public class OrdenCompraMapper {
 
    public static OrdenCompraDto toDto(OrdenCompra o) {
        if (o == null) return null;
        Long proveedorId = o.getProveedor() == null ? null : o.getProveedor().getId();
        String proveedorNombre = o.getProveedor() == null ? null : o.getProveedor().getNombreRazonSocial();
        Long solicitanteId = o.getSolicitante() == null ? null : o.getSolicitante().getId();
        String solicitanteNombre = o.getSolicitante() == null ? null :
                o.getSolicitante().getPersona() == null ? null :
                o.getSolicitante().getPersona().getNombreRazonSocial();
 
        return new OrdenCompraDto(
            o.getId(),
            o.getCodigo(),
            proveedorId,
            proveedorNombre,
            solicitanteId,
            solicitanteNombre,
            o.getEstado() == null ? null : o.getEstado().name(),
            o.getMoneda() == null ? null : o.getMoneda().name(),
            o.getTipoCambio(),
            o.getSubtotal(),
            o.getIgv(),
            o.getTotal(),
            o.getFechaEmision(),
            o.getFechaEntregaEstimada(),
            o.getFechaEntregaReal(),
            o.getObservaciones(),
            o.getFechaRegistro(),
            o.getDetalles() == null ? null : o.getDetalles().stream()
                    .map(DetalleOrdenCompraMapper::toDto)
                    .collect(Collectors.toList())
        );
    }
 
    public static OrdenCompra toEntity(OrdenCompraDto d) {
        if (d == null) return null;
        OrdenCompra o = new OrdenCompra();
        o.setId(d.getId());
        o.setCodigo(d.getCodigo());
        if (d.getProveedorId() != null) {
            Persona proveedor = new Persona();
            proveedor.setId(d.getProveedorId());
            o.setProveedor(proveedor);
        }
        if (d.getSolicitanteId() != null) {
            Trabajador solicitante = new Trabajador();
            solicitante.setId(d.getSolicitanteId());
            o.setSolicitante(solicitante);
        }
        if (d.getEstado() != null)
            o.setEstado(OrdenCompra.EstadoOC.valueOf(d.getEstado()));
        if (d.getMoneda() != null)
            o.setMoneda(OrdenCompra.Moneda.valueOf(d.getMoneda()));
        o.setTipoCambio(d.getTipoCambio());
        o.setSubtotal(d.getSubtotal());
        o.setIgv(d.getIgv());
        o.setTotal(d.getTotal());
        o.setFechaEmision(d.getFechaEmision());
        o.setFechaEntregaEstimada(d.getFechaEntregaEstimada());
        o.setFechaEntregaReal(d.getFechaEntregaReal());
        o.setObservaciones(d.getObservaciones());
        return o;
    }
}