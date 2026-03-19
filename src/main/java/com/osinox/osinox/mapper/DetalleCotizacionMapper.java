package com.osinox.osinox.mapper;

import com.osinox.osinox.dto.DetalleCotizacionDto;
import com.osinox.osinox.entity.DetalleCotizacion;

public class DetalleCotizacionMapper {
    public static DetalleCotizacionDto toDto(DetalleCotizacion dc) {
        if (dc == null) return null;
        Long productoId = dc.getProducto() == null ? null : dc.getProducto().getId();
        return new DetalleCotizacionDto(
            dc.getId(),
            productoId,
            dc.getDescripcion(),
            dc.getUnidad(),
            dc.getCantidad(),
            dc.getPrecioUnitario(),
            dc.getDescuento(),
            dc.getSubtotal()
        );
    }
    public static DetalleCotizacion toEntity(DetalleCotizacionDto d) {
        if (d == null) return null;
        DetalleCotizacion dc = new DetalleCotizacion();
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
        dc.setSubtotal(d.getSubtotal());
        return dc;
    }
}
