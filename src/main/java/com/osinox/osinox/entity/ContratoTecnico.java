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
@Table(name = "contrato_tecnico")
public class ContratoTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Persona cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 15)
    private TipoContrato tipo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "monto", precision = 12, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", length = 5)
    private Moneda moneda;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoContrato estado;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "archivo_url", length = 500)
    private String archivoUrl;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoContrato.BORRADOR;
        if (this.moneda == null) this.moneda = Moneda.PEN;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum TipoContrato { SERVICIO, SUMINISTRO, MIXTO }
    public enum EstadoContrato { BORRADOR, ACTIVO, VENCIDO, CANCELADO }
    public enum Moneda { PEN, USD }
}