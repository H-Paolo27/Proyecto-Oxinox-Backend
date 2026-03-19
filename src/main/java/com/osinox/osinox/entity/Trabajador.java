package com.osinox.osinox.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "persona")
@ToString(exclude = "persona")
@Entity
@Table(name = "trabajador")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false, unique = true)
    private Persona persona;

    @Column(name = "cargo", nullable = false, length = 100)
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pension", length = 10)
    private TipoPension tipoPension;

    @Column(name = "nombre_afp", length = 50)
    private String nombreAfp;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @Column(name = "fecha_cese")
    private LocalDate fechaCese;

    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoTrabajador estado;

    @PrePersist
    public void prePersist() {
        if (this.tipoPension == null) this.tipoPension = TipoPension.ONP;
        if (this.estado == null) this.estado = EstadoTrabajador.ACTIVO;
    }

    public enum TipoPension { ONP, AFP }
    public enum EstadoTrabajador { ACTIVO, INACTIVO, LICENCIA }
}