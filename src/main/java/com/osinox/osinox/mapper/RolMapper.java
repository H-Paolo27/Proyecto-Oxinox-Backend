package com.osinox.osinox.mapper;
import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.dto.RolDto;
public class RolMapper {
    public static RolDto toDto(Rol r) {
        if (r == null) return null;
        return new RolDto(r.getId(), r.getNombre(), r.getDescripcion());
    }
    public static Rol toEntity(RolDto d) {
        if (d == null) return null;
        Rol r = new Rol();
        r.setId(d.getId());
        r.setNombre(d.getNombre());
        r.setDescripcion(d.getDescripcion());
        return r;
    }
}
