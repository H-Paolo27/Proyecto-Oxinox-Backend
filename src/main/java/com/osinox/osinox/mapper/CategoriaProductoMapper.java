package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.CategoriaProductoDto;
import com.osinox.osinox.entity.CategoriaProducto;
public class CategoriaProductoMapper {
    public static CategoriaProductoDto toDto(CategoriaProducto c) {
        if (c == null) return null;
        return new CategoriaProductoDto(c.getId(), c.getNombre(), c.getDescripcion());
    }
    public static CategoriaProducto toEntity(CategoriaProductoDto d) {
        if (d == null) return null;
        CategoriaProducto c = new CategoriaProducto();
        c.setId(d.getId());
        c.setNombre(d.getNombre());
        c.setDescripcion(d.getDescripcion());
        return c;
    }
}
