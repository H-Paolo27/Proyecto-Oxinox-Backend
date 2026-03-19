package com.osinox.osinox.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orden","producto"})
@ToString(exclude = {"orden","producto"})
@Entity
@Table(name = "detalle_orden_produccion")
public class DetalleOrdenProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenProduccion orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "cantidad", precision = 10, scale = 3)
    private BigDecimal cantidad;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "completado")
    private Boolean completado;

    @PrePersist
    public void prePersist() {
        if (this.completado == null) this.completado = false;
    }
}