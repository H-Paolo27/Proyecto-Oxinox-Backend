package com.osinox.osinox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String emailAcceso;          // <- agregar
    private java.util.List<String> roles;
}