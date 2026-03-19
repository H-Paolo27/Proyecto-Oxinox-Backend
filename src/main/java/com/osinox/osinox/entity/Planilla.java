package com.osinox.osinox.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "trabajador")
@ToString(exclude = "trabajador")
@Entity
@Table(name = "planilla",
    uniqueConstraints = @UniqueConstraint(columnNames = {"periodo","id_trabajador"})
)
public class Planilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planilla")
    private Long id;

    @Column(name = "periodo", nullable = false, length = 7)  // '2026-03'
    private String periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @Column(name = "sueldo_base", precision = 10, scale = 2)
    private BigDecimal sueldoBase;

    @Column(name = "dias_trabajados")
    private Integer diasTrabajados;

    @Column(name = "horas_extra", precision = 5, scale = 2)
    private BigDecimal horasExtra;

    @Column(name = "monto_horas_extra", precision = 10, scale = 2)
    private BigDecimal montoHorasExtra;

    @Column(name = "gratificacion", precision = 10, scale = 2)
    private BigDecimal gratificacion;

    @Column(name = "cts", precision = 10, scale = 2)
    private BigDecimal cts;

    @Column(name = "asignacion_familiar", precision = 10, scale = 2)
    private BigDecimal asignacionFamiliar;

    @Column(name = "essalud", precision = 10, scale = 2)
    private BigDecimal essalud;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pension", length = 5)
    private TipoPension tipoPension;

    @Column(name = "descuento_pension", precision = 10, scale = 2)
    private BigDecimal descuentoPension;

    @Column(name = "renta_quinta", precision = 10, scale = 2)
    private BigDecimal rentaQuinta;

    @Column(name = "otros_descuentos", precision = 10, scale = 2)
    private BigDecimal otrosDescuentos;

    @Column(name = "total_bruto", precision = 10, scale = 2)
    private BigDecimal totalBruto;

    @Column(name = "total_descuentos", precision = 10, scale = 2)
    private BigDecimal totalDescuentos;

    @Column(name = "total_neto", precision = 10, scale = 2)
    private BigDecimal totalNeto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 10)
    private EstadoPlanilla estado;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) this.estado = EstadoPlanilla.BORRADOR;
        if (this.diasTrabajados == null) this.diasTrabajados = 30;
        if (this.horasExtra == null) this.horasExtra = BigDecimal.ZERO;
        if (this.montoHorasExtra == null) this.montoHorasExtra = BigDecimal.ZERO;
        if (this.gratificacion == null) this.gratificacion = BigDecimal.ZERO;
        if (this.cts == null) this.cts = BigDecimal.ZERO;
        if (this.asignacionFamiliar == null) this.asignacionFamiliar = BigDecimal.ZERO;
        if (this.rentaQuinta == null) this.rentaQuinta = BigDecimal.ZERO;
        if (this.otrosDescuentos == null) this.otrosDescuentos = BigDecimal.ZERO;
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum TipoPension { ONP, AFP }
    public enum EstadoPlanilla { BORRADOR, APROBADO, CERRADO, PAGADO, ANULADO }
}