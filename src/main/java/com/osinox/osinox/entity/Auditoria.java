package com.osinox.osinox.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "usuario")
@ToString(exclude = "usuario")
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "accion", length = 50)
    private String accion;           // CREATE, UPDATE, DELETE

    @Column(name = "entidad", length = 50)
    private String entidad;          // 'proyecto', 'comprobante', etc.

    @Column(name = "entidad_id")
    private Long entidadId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalle", columnDefinition = "JSON")
    private String detalle;          // JSON como String, parsear con ObjectMapper si se necesita

    @Column(name = "ip", length = 45)
    private String ip;

    @Column(name = "fecha", updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}