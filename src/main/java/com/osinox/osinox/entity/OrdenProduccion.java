package com.osinox.osinox.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"proyecto","responsable","detalles"})
@EqualsAndHashCode(exclude = {"proyecto","responsable","detalles"})
@Entity
@Table(name = "orden_produccion")
public class OrdenProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoOrden estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 10)
    private Prioridad prioridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable")
    private Trabajador responsable;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private LocalDate fechaFinEstimada;

    @Column(name = "fecha_fin_real")
    private LocalDate fechaFinReal;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenProduccion> detalles;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoOrden.PENDIENTE;
        if (this.prioridad == null) this.prioridad = Prioridad.NORMAL;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum EstadoOrden { PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO }
    public enum Prioridad { BAJA, NORMAL, ALTA, URGENTE }
}