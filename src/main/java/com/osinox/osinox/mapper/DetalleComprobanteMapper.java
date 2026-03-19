package com.osinox.osinox.mapper;

import com.osinox.osinox.dto.DetalleComprobanteDto;
import com.osinox.osinox.entity.DetalleComprobante;

public class DetalleComprobanteMapper {
    public static DetalleComprobanteDto toDto(DetalleComprobante dc) {
        if (dc == null) return null;
        Long productoId = dc.getProducto() == null ? null : dc.getProducto().getId();
        return new DetalleComprobanteDto(
            dc.getId(),
            productoId,
            dc.getDescripcion(),
            dc.getUnidad(),
            dc.getCantidad(),
            dc.getPrecioUnitario(),
            dc.getDescuento(),
            dc.getIgvUnitario(),
            dc.getSubtotal()
        );
    }
    public static DetalleComprobante toEntity(DetalleComprobanteDto d) {
        if (d == null) return null;
        DetalleComprobante dc = new DetalleComprobante();
        dc.setId(d.getId());
        if (d.getProductoId() != null) {
            com.osinox.osinox.entity.Producto p = new com.osinox.osinox.entity.Producto();
            p.setId(d.getProductoId());
            dc.setProducto(p);
        }
        dc.setDescripcion(d.getDescripcion());
        dc.setUnidad(d.getUnidad());
        dc.setCantidad(d.getCantidad());
        dc.setPrecioUnitario(d.getPrecioUnitario());
        dc.setDescuento(d.getDescuento());
        dc.setIgvUnitario(d.getIgvUnitario());
        dc.setSubtotal(d.getSubtotal());
        return dc;
    }
}
