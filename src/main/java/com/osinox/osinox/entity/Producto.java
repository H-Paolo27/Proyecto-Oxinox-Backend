package com.osinox.osinox.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoria")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "producto")
public class Producto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 200)   // ← fix aquí
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaProducto categoria;

    @Column(name = "unidad_medida", length = 30)
    private String unidadMedida;

    @Column(name = "stock_actual", precision = 10, scale = 3)
    private BigDecimal stockActual;

    @Column(name = "stock_minimo", precision = 10, scale = 3)
    private BigDecimal stockMinimo;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.stockActual == null) this.stockActual = BigDecimal.ZERO;
        if (this.stockMinimo == null) this.stockMinimo = BigDecimal.ZERO;
        if (this.estado == null) this.estado = true;
        this.fechaRegistro = LocalDateTime.now();
    }
}