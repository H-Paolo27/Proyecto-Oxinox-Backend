package com.osinox.osinox.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Persona cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable")
    private Trabajador responsable;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20)
    private TipoProyecto tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoProyecto estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 10)
    private Prioridad prioridad;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private LocalDate fechaFinEstimada;

    @Column(name = "fecha_fin_real")
    private LocalDate fechaFinReal;

    @Column(name = "presupuesto", precision = 12, scale = 2)
    private BigDecimal presupuesto;

    @Column(name = "costo_real", precision = 12, scale = 2)
    private BigDecimal costoReal;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoProyecto.COTIZACION;
        if (this.prioridad == null) this.prioridad = Prioridad.NORMAL;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum TipoProyecto { FABRICACION, MANTENIMIENTO, INSTALACION, MIXTO, OTRO }
    public enum EstadoProyecto { COTIZACION, APROBADO, EN_PROCESO, PAUSADO, COMPLETADO, CANCELADO }
    public enum Prioridad { BAJA, NORMAL, ALTA, URGENTE }
}