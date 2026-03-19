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
@ToString(exclude = {"proveedor","solicitante","detalles"})
@EqualsAndHashCode(exclude = {"proveedor","solicitante","detalles"})
@Entity
@Table(name = "orden_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_compra")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Persona proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitante")
    private Trabajador solicitante;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoOC estado;

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

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;

    @Column(name = "fecha_entrega_real")
    private LocalDate fechaEntregaReal;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenCompra> detalles;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoOC.BORRADOR;
        if (this.moneda == null) this.moneda = Moneda.PEN;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum EstadoOC { BORRADOR, APROBADA, ENVIADA, RECIBIDA, CANCELADA }
    public enum Moneda { PEN, USD }
}