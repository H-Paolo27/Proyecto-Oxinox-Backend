package com.osinox.osinox.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

// ─────────────────────────────────────────────────
// ARCHIVO - Referencias Firebase Storage
// ─────────────────────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo")
    private Long id;

    @Column(name = "nombre_original", length = 200)
    private String nombreOriginal;

    @Column(name = "nombre_storage", nullable = false, length = 200)
    private String nombreStorage;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "tipo_mime", length = 100)
    private String tipoMime;

    @Column(name = "tamanio_bytes")
    private Long tamanioBytes;

    @Column(name = "entidad_tipo", length = 50)
    private String entidadTipo;       // 'proyecto','comprobante','producto', etc.

    @Column(name = "entidad_id")
    private Long entidadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}