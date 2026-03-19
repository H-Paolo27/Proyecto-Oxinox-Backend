package com.osinox.osinox.mapper;

import com.osinox.osinox.entity.DetalleGuiaRemision;
import com.osinox.osinox.entity.Producto;
import com.osinox.osinox.dto.DetalleGuiaRemisionDto;

public class DetalleGuiaRemisionMapper {

    public static DetalleGuiaRemisionDto toDto(DetalleGuiaRemision dg) {
        if (dg == null) return null;
        Long productoId = dg.getProducto() == null ? null : dg.getProducto().getId();
        return new DetalleGuiaRemisionDto(
            dg.getId(),
            productoId,
            dg.getCodigo(),           // estaba como getDescripcion() antes — incorrecto
            dg.getDescripcion(),
            dg.getUnidadMedida(),     // era getUnidad() — no existe en entidad
            dg.getCantidad(),
            dg.getPesoTotal()         // era getPesoBruto() — no existe en entidad
        );
    }

    public static DetalleGuiaRemision toEntity(DetalleGuiaRemisionDto d) {
        if (d == null) return null;
        DetalleGuiaRemision dg = new DetalleGuiaRemision();
        dg.setId(d.getId());
        if (d.getProductoId() != null) {
            Producto p = new Producto();
            p.setId(d.getProductoId());
            dg.setProducto(p);
        }
        dg.setCodigo(d.getCodigo());
        dg.setDescripcion(d.getDescripcion());
        dg.setUnidadMedida(d.getUnidadMedida());  // era setUnidad() — no existe
        dg.setCantidad(d.getCantidad());
        dg.setPesoTotal(d.getPesoTotal());         // era setPesoBruto() — no existe
        return dg;
    }
}