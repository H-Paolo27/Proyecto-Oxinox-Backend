package com.osinox.osinox.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"guia","producto"})
@ToString(exclude = {"guia","producto"})
@Entity
@Table(name = "detalle_guia_remision")
public class DetalleGuiaRemision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_guia", nullable = false)
    private GuiaRemision guia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    @Column(name = "unidad_medida", length = 30)
    private String unidadMedida;

    @Column(name = "cantidad", precision = 10, scale = 3)
    private BigDecimal cantidad;

    @Column(name = "peso_total", precision = 10, scale = 3)
    private BigDecimal pesoTotal;
}