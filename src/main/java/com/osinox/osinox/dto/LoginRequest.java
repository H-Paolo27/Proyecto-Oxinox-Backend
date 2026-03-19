package com.osinox.osinox.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String identifier;   // puede ser username O email
    private String password;
}
