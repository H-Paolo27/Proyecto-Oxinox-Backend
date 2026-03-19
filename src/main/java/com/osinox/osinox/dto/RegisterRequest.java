package com.osinox.osinox.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;  // <- agregar
}