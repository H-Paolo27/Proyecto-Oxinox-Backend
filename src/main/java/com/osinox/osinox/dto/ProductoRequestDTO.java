package com.osinox.osinox.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200)
    private String nombre;

    private String descripcion;

    private Long idCategoria;

    @Size(max = 30)
    private String unidadMedida;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal stockMinimo;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal precioUnitario;

    private Boolean estado = true;
    // imagen_url se maneja aparte (multipart upload)
}