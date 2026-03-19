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
@Table(name = "mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Persona cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 15)
    private TipoMantenimiento tipo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico")
    private Trabajador tecnico;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoMantenimiento estado;

    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;

    @Column(name = "fecha_realizada")
    private LocalDate fechaRealizada;

    @Column(name = "costo", precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "informe_url", length = 500)
    private String informeUrl;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoMantenimiento.PENDIENTE;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum TipoMantenimiento { PREVENTIVO, CORRECTIVO, PREDICTIVO }
    public enum EstadoMantenimiento { PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO }
}