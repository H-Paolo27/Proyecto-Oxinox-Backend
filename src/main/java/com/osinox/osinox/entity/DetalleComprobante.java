package com.osinox.osinox.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"comprobante","producto"})
@ToString(exclude = {"comprobante","producto"})
@Entity
@Table(name = "detalle_comprobante")
public class DetalleComprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comprobante", nullable = false)
    private Comprobante comprobante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "cantidad", precision = 10, scale = 3)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "descuento", precision = 5, scale = 2)
    private BigDecimal descuento;

    @Column(name = "igv_unitario", precision = 10, scale = 2)
    private BigDecimal igvUnitario;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    public void prePersist() {
        if (this.descuento == null) this.descuento = BigDecimal.ZERO;
    }
}