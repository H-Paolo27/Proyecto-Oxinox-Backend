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
@ToString(exclude = {"persona","cotizacion","proyecto","detalles"})
@EqualsAndHashCode(exclude = {"persona","cotizacion","proyecto","detalles"})
@Entity
@Table(name = "comprobante")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoComprobante tipo;

    @Column(name = "serie", nullable = false, length = 4)
    private String serie;

    @Column(name = "correlativo", nullable = false, length = 8)
    private String correlativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion")
    private Cotizacion cotizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @Column(name = "ruc_emisor", length = 11)
    private String rucEmisor;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv", precision = 12, scale = 2)
    private BigDecimal igv;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", length = 5)
    private Moneda moneda;

    @Column(name = "tipo_cambio", precision = 6, scale = 3)
    private BigDecimal tipoCambio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoComprobante estado;

    @Column(name = "hash_cdr", length = 200)
    private String hashCdr;

    @Column(name = "xml_url", length = 500)
    private String xmlUrl;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleComprobante> detalles;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoComprobante.BORRADOR;
        if (this.moneda == null) this.moneda = Moneda.PEN;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum TipoComprobante { FACTURA, BOLETA, NOTA_CREDITO, NOTA_DEBITO }
    public enum EstadoComprobante { BORRADOR, ENVIADO_SUNAT, ACEPTADO, RECHAZADO, ANULADO }
    public enum Moneda { PEN, USD }
}