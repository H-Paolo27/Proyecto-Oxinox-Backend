// ─────────────────────────────────────────────────────────────
// NotificacionDto.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.dto;
 
import lombok.*;
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private String tipo;
    private Boolean leida;
    private String entidadTipo;
    private Long entidadId;
    private LocalDateTime fechaRegistro;
}