// ─────────────────────────────────────────────────────────────
// CategoriaProductoDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
 