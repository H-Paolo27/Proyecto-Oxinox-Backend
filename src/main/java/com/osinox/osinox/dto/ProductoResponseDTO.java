package com.osinox.osinox.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoResponseDTO {

    private Long idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoriaNombre;
    private Long idCategoria;
    private String unidadMedida;
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
    private BigDecimal precioUnitario;
    private String imagenUrl;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
    private Boolean stockBajoMinimo;  // campo calculado útil para admin
}