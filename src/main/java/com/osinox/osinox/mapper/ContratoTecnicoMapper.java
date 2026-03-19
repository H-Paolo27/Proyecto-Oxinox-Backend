package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.ContratoTecnicoDto;
import com.osinox.osinox.entity.ContratoTecnico;
public class ContratoTecnicoMapper {
    public static ContratoTecnicoDto toDto(ContratoTecnico ct) {
        if (ct == null) return null;
        Long clienteId = ct.getCliente() == null ? null : ct.getCliente().getId();
        String clienteNombre = ct.getCliente() == null ? null : ct.getCliente().getNombreRazonSocial();
        Long proyectoId = ct.getProyecto() == null ? null : ct.getProyecto().getId();
        String proyectoNombre = ct.getProyecto() == null ? null : ct.getProyecto().getNombre();
        String tipo = ct.getTipo() == null ? null : ct.getTipo().name();
        String moneda = ct.getMoneda() == null ? null : ct.getMoneda().name();
        String estado = ct.getEstado() == null ? null : ct.getEstado().name();
        return new ContratoTecnicoDto(
            ct.getId(),
            ct.getCodigo(),
            clienteId,
            clienteNombre,
            proyectoId,
            proyectoNombre,
            tipo,
            ct.getDescripcion(),
            ct.getMonto(),
            moneda,
            estado,
            ct.getFechaInicio(),
            ct.getFechaFin(),
            ct.getArchivoUrl(),
            ct.getObservaciones(),
            ct.getFechaRegistro()
        );
    }
    public static ContratoTecnico toEntity(ContratoTecnicoDto d) {
        if (d == null) return null;
        ContratoTecnico ct = new ContratoTecnico();
        ct.setId(d.getId());
        ct.setCodigo(d.getCodigo());
        if (d.getClienteId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getClienteId());
            ct.setCliente(p);
        }
        if (d.getProyectoId() != null) {
            com.osinox.osinox.entity.Proyecto proj = new com.osinox.osinox.entity.Proyecto();
            proj.setId(d.getProyectoId());
            ct.setProyecto(proj);
        }
        try {
            if (d.getTipo() != null) ct.setTipo(ContratoTecnico.TipoContrato.valueOf(d.getTipo()));
        } catch (IllegalArgumentException ex) {}
        ct.setDescripcion(d.getDescripcion());
        ct.setMonto(d.getMonto());
        try {
            if (d.getMoneda() != null) ct.setMoneda(ContratoTecnico.Moneda.valueOf(d.getMoneda()));
        } catch (IllegalArgumentException ex) {}
        try {
            if (d.getEstado() != null) ct.setEstado(ContratoTecnico.EstadoContrato.valueOf(d.getEstado()));
        } catch (IllegalArgumentException ex) {}
        ct.setFechaInicio(d.getFechaInicio());
        ct.setFechaFin(d.getFechaFin());
        ct.setArchivoUrl(d.getArchivoUrl());
        ct.setObservaciones(d.getObservaciones());
        ct.setFechaRegistro(d.getFechaRegistro());
        return ct;
    }
}
