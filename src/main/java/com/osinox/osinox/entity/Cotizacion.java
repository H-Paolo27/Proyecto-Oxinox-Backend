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
@ToString(exclude = {"cliente","vendedor","proyecto","detalles"})
@EqualsAndHashCode(exclude = {"cliente","vendedor","proyecto","detalles"})
@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Persona cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor")
    private Trabajador vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoCotizacion estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", length = 5)
    private Moneda moneda;

    @Column(name = "tipo_cambio", precision = 6, scale = 3)
    private BigDecimal tipoCambio;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv", precision = 12, scale = 2)
    private BigDecimal igv;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "condiciones_pago", length = 200)
    private String condicionesPago;

    @Column(name = "tiempo_entrega", length = 100)
    private String tiempoEntrega;

    @Column(name = "validez_dias")
    private Integer validezDias;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCotizacion> detalles;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoCotizacion.BORRADOR;
        if (this.moneda == null) this.moneda = Moneda.PEN;
        if (this.validezDias == null) this.validezDias = 30;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum EstadoCotizacion { BORRADOR, ENVIADA, APROBADA, RECHAZADA, VENCIDA }
    public enum Moneda { PEN, USD }
}