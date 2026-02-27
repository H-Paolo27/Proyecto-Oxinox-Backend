package com.osinox.osinox.entity;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"usuario", "trabajador"})
@ToString(exclude = {"usuario", "trabajador"})
@Entity
@Table(name="persona")

public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona", nullable = false)
    private TipoPersona tipoPersona;

    @Column(name = "nombre_razon_social", nullable = false, length = 200)
    private String nombreRazonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", unique = true, length = 20)
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relacion", nullable = false)
    private TipoRelacion tipoRelacion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // Relación 1 a 1 con Usuario
    @OneToOne(mappedBy = "persona")
    private Usuario usuario;

    // Relación 1 a 1 con Trabajador
    @OneToOne(mappedBy = "persona")
    private Trabajador trabajador;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = true;
    }

    public enum TipoPersona {
        NATURAL,
        JURIDICA,
        GUBERNAMENTAL
    }

    public enum TipoDocumento {
        DNI,
        RUC,
        CE,
        OTRO
    }

    public enum TipoRelacion {
        CLIENTE,
        PROVEEDOR,
        TRABAJADOR_INTERNO,
        MIXTO
    }
}