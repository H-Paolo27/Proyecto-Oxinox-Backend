package com.osinox.osinox.mapper;
import java.math.BigDecimal;

import com.osinox.osinox.dto.ProductoDto;
import com.osinox.osinox.entity.CategoriaProducto;
import com.osinox.osinox.entity.Producto;
public class ProductoMapper {
    public static ProductoDto toDto(Producto p) {
        if (p == null) return null;
        Long categoriaId = p.getCategoria() == null ? null : p.getCategoria().getId();
        String categoriaNombre = p.getCategoria() == null ? null : p.getCategoria().getNombre();
        return new ProductoDto(
            p.getId(),
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            categoriaId,
            categoriaNombre,
            p.getUnidadMedida(),
            p.getStockActual(),
            p.getStockMinimo(),
            p.getPrecioUnitario(),
            p.getImagenUrl(),
            p.getEstado(),
            p.getFechaRegistro()
        );
    }
    public static Producto toEntity(ProductoDto d) {
        if (d == null) return null;
        Producto p = new Producto();
        p.setId(d.getId());
        p.setCodigo(d.getCodigo());
        p.setNombre(d.getNombre());
        p.setDescripcion(d.getDescripcion());
        if (d.getCategoriaId() != null) {
            CategoriaProducto cat = new CategoriaProducto();
            cat.setId(d.getCategoriaId());
            p.setCategoria(cat);
        }
        p.setUnidadMedida(d.getUnidadMedida());
        p.setStockActual(d.getStockActual() != null ? d.getStockActual() : BigDecimal.ZERO);
        p.setStockMinimo(d.getStockMinimo() != null ? d.getStockMinimo() : BigDecimal.ZERO);
        p.setPrecioUnitario(d.getPrecioUnitario());
        p.setImagenUrl(d.getImagenUrl());
        p.setEstado(d.getEstado());
        p.setFechaRegistro(d.getFechaRegistro());
        return p;
    }
}
