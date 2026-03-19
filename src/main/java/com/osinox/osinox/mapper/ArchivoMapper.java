package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.ArchivoDto;
import com.osinox.osinox.entity.Archivo;
public class ArchivoMapper {
    public static ArchivoDto toDto(Archivo a) {
        if (a == null) return null;
        Long usuarioId = a.getUsuario() == null ? null : a.getUsuario().getId();
        return new ArchivoDto(
            a.getId(),
            a.getNombreOriginal(),
            a.getUrl(),
            a.getTipoMime(),
            a.getTamanioBytes(),
            a.getEntidadTipo(),
            a.getEntidadId(),
            usuarioId,
            a.getFechaRegistro()
        );
    }
    public static Archivo toEntity(ArchivoDto d) {
        if (d == null) return null;
        Archivo a = new Archivo();
        a.setId(d.getId());
        a.setNombreOriginal(d.getNombreOriginal());
        a.setNombreStorage(d.getNombreOriginal()); // assume similar
        a.setUrl(d.getUrl());
        a.setTipoMime(d.getTipoMime());
        a.setTamanioBytes(d.getTamanioBytes());
        a.setEntidadTipo(d.getEntidadTipo());
        a.setEntidadId(d.getEntidadId());
        if (d.getUsuarioId() != null) {
            com.osinox.osinox.entity.Usuario u = new com.osinox.osinox.entity.Usuario();
            u.setId(d.getUsuarioId());
            a.setUsuario(u);
        }
        a.setFechaRegistro(d.getFechaRegistro());
        return a;
    }
}
