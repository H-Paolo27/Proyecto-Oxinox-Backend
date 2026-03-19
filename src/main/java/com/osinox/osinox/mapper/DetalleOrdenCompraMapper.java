// ─────────────────────────────────────────────────────────────
// DetalleOrdenCompraMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.DetalleOrdenCompra;
import com.osinox.osinox.entity.Producto;
import com.osinox.osinox.dto.DetalleOrdenCompraDto;
 
public class DetalleOrdenCompraMapper {
 
    public static DetalleOrdenCompraDto toDto(DetalleOrdenCompra d) {
        if (d == null) return null;
        Long productoId = d.getProducto() == null ? null : d.getProducto().getId();
        return new DetalleOrdenCompraDto(
            d.getId(),
            productoId,
            d.getDescripcion(),
            d.getCantidad(),
            d.getUnidad(),
            d.getPrecioUnitario(),
            d.getSubtotal(),
            d.getRecibido()
        );
    }
 
    public static DetalleOrdenCompra toEntity(DetalleOrdenCompraDto d) {
        if (d == null) return null;
        DetalleOrdenCompra dc = new DetalleOrdenCompra();
        dc.setId(d.getId());
        if (d.getProductoId() != null) {
            Producto p = new Producto();
            p.setId(d.getProductoId());
            dc.setProducto(p);
        }
        dc.setDescripcion(d.getDescripcion());
        dc.setCantidad(d.getCantidad());
        dc.setUnidad(d.getUnidad());
        dc.setPrecioUnitario(d.getPrecioUnitario());
        dc.setSubtotal(d.getSubtotal());
        dc.setRecibido(d.getRecibido());
        return dc;
    }
}
 