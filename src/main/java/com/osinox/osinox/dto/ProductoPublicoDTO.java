package com.osinox.osinox.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoPublicoDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String categoriaNombre;
    private String unidadMedida;
    private String imagenUrl;
    // SIN: precio, stock, codigo, estado, fechaRegistro
}