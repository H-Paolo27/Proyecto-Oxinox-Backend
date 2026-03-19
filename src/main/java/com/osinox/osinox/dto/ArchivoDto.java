// ─────────────────────────────────────────────────────────────
// ArchivoDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoDto {
    private Long id;
    private String nombreOriginal;
    private String url;
    private String tipoMime;
    private Long tamanioBytes;
    private String entidadTipo;
    private Long entidadId;
    private Long usuarioId;
    private LocalDateTime fechaRegistro;
}