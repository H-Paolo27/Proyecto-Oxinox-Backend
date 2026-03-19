package com.osinox.osinox.mapper;
import com.osinox.osinox.dto.GuiaRemisionDto;
import com.osinox.osinox.entity.GuiaRemision;
public class GuiaRemisionMapper {
    public static GuiaRemisionDto toDto(GuiaRemision g) {
        if (g == null) return null;
        Long remitenteId = g.getRemitente() == null ? null : g.getRemitente().getId();
        String remitenteNombre = g.getRemitente() == null ? null : g.getRemitente().getNombreRazonSocial();
        Long destinatarioId = g.getDestinatario() == null ? null : g.getDestinatario().getId();
        String destinatarioNombre = g.getDestinatario() == null ? null : g.getDestinatario().getNombreRazonSocial();
        Long proyectoId = g.getProyecto() == null ? null : g.getProyecto().getId();
        Long comprobanteId = g.getComprobante() == null ? null : g.getComprobante().getId();
        String motivoTraslado = g.getMotivoTraslado() == null ? null : g.getMotivoTraslado().name();
        String modalidadTransporte = g.getModalidadTransporte() == null ? null : g.getModalidadTransporte().name();
        String estado = g.getEstado() == null ? null : g.getEstado().name();
        return new GuiaRemisionDto(
            g.getId(),
            g.getSerie(),
            g.getCorrelativo(),
            remitenteId,
            remitenteNombre,
            destinatarioId,
            destinatarioNombre,
            proyectoId,
            comprobanteId,
            g.getRucEmisor(),
            g.getPuntoPartida(),
            g.getPuntoLlegada(),
            g.getFechaTraslado(),
            motivoTraslado,
            modalidadTransporte,
            g.getRucTransportista(),
            g.getNombreTransportista(),
            g.getNumLicenciaConducir(),
            g.getNumConstanciaInscripcion(),
            g.getMarcaVehiculo(),
            g.getPlacaVehiculo(),
            estado,
            g.getPdfUrl(),
            g.getObservaciones(),
            g.getFechaRegistro(),
            null // detalles null
        );
    }
    public static GuiaRemision toEntity(GuiaRemisionDto d) {
        if (d == null) return null;
        GuiaRemision g = new GuiaRemision();
        g.setId(d.getId());
        g.setSerie(d.getSerie());
        g.setCorrelativo(d.getCorrelativo());
        if (d.getRemitenteId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getRemitenteId());
            g.setRemitente(p);
        }
        if (d.getDestinatarioId() != null) {
            com.osinox.osinox.entity.Persona p = new com.osinox.osinox.entity.Persona();
            p.setId(d.getDestinatarioId());
            g.setDestinatario(p);
        }
        if (d.getProyectoId() != null) {
            com.osinox.osinox.entity.Proyecto proj = new com.osinox.osinox.entity.Proyecto();
            proj.setId(d.getProyectoId());
            g.setProyecto(proj);
        }
        if (d.getComprobanteId() != null) {
            com.osinox.osinox.entity.Comprobante comp = new com.osinox.osinox.entity.Comprobante();
            comp.setId(d.getComprobanteId());
            g.setComprobante(comp);
        }
        g.setRucEmisor(d.getRucEmisor());
        g.setPuntoPartida(d.getPuntoPartida());
        g.setPuntoLlegada(d.getPuntoLlegada());
        g.setFechaTraslado(d.getFechaTraslado());
        try {
            if (d.getMotivoTraslado() != null) g.setMotivoTraslado(GuiaRemision.MotivoTraslado.valueOf(d.getMotivoTraslado()));
        } catch (IllegalArgumentException ex) {}
        try {
            if (d.getModalidadTransporte() != null) g.setModalidadTransporte(GuiaRemision.ModalidadTransporte.valueOf(d.getModalidadTransporte()));
        } catch (IllegalArgumentException ex) {}
        g.setRucTransportista(d.getRucTransportista());
        g.setNombreTransportista(d.getNombreTransportista());
        g.setNumLicenciaConducir(d.getNumLicenciaConducir());
        g.setNumConstanciaInscripcion(d.getNumConstanciaInscripcion());
        g.setMarcaVehiculo(d.getMarcaVehiculo());
        g.setPlacaVehiculo(d.getPlacaVehiculo());
        try {
            if (d.getEstado() != null) g.setEstado(GuiaRemision.EstadoGuia.valueOf(d.getEstado()));
        } catch (IllegalArgumentException ex) {}
        g.setPdfUrl(d.getPdfUrl());
        g.setObservaciones(d.getObservaciones());
        g.setFechaRegistro(d.getFechaRegistro());
        return g;
    }
}
