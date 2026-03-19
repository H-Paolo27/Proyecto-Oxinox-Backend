package com.osinox.osinox.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"remitente","destinatario","proyecto","comprobante","detalles"})
@EqualsAndHashCode(exclude = {"remitente","destinatario","proyecto","comprobante","detalles"})
@Entity
@Table(name = "guia_remision")
public class GuiaRemision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_guia")
    private Long id;

    @Column(name = "serie", nullable = false, length = 4)
    private String serie;

    @Column(name = "correlativo", nullable = false, length = 8)
    private String correlativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_remitente", nullable = false)
    private Persona remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Persona destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comprobante")
    private Comprobante comprobante;

    @Column(name = "ruc_emisor", length = 11)
    private String rucEmisor;

    @Column(name = "punto_partida", length = 200)
    private String puntoPartida;

    @Column(name = "punto_llegada", length = 200)
    private String puntoLlegada;

    @Column(name = "fecha_traslado", nullable = false)
    private LocalDate fechaTraslado;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_traslado", nullable = false, length = 30)
    private MotivoTraslado motivoTraslado;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad_transporte", length = 10)
    private ModalidadTransporte modalidadTransporte;

    @Column(name = "ruc_transportista", length = 11)
    private String rucTransportista;

    @Column(name = "nombre_transportista", length = 200)
    private String nombreTransportista;

    @Column(name = "num_licencia_conducir", length = 30)
    private String numLicenciaConducir;

    @Column(name = "num_constancia_inscripcion", length = 50)
    private String numConstanciaInscripcion;

    @Column(name = "marca_vehiculo", length = 50)
    private String marcaVehiculo;

    @Column(name = "placa_vehiculo", length = 20)
    private String placaVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoGuia estado;

    @Column(name = "hash_cdr", length = 200)
    private String hashCdr;

    @Column(name = "xml_url", length = 500)
    private String xmlUrl;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "guia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleGuiaRemision> detalles;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoGuia.BORRADOR;
        if (this.modalidadTransporte == null) this.modalidadTransporte = ModalidadTransporte.PRIVADO;
        if (this.motivoTraslado == null) this.motivoTraslado = MotivoTraslado.VENTA;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum MotivoTraslado {
        VENTA, COMPRA, DEVOLUCION, CONSIGNACION,
        TRANSFORMACION, ENTRE_ESTABLECIMIENTOS, EXPORTACION, OTROS
    }
    public enum ModalidadTransporte { PRIVADO, PUBLICO }
    public enum EstadoGuia { BORRADOR, ENVIADO_SUNAT, ACEPTADO, RECHAZADO, ANULADO }
}