package com.osinox.osinox.controller;

import com.osinox.osinox.dto.JwtResponse;
import com.osinox.osinox.dto.LoginRequest;
import com.osinox.osinox.dto.RegisterRequest;
import com.osinox.osinox.dto.UsuarioDto;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.mapper.UsuarioMapper;
import com.osinox.osinox.repository.UsuarioRepository;
import com.osinox.osinox.security.JwtUtil;
import com.osinox.osinox.service.CustomUserDetailsService;
import com.osinox.osinox.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDto> register(@RequestBody RegisterRequest request) {
        Usuario usuario = usuarioService.registerCliente(
            request.getUsername(),
            request.getPassword(),
            request.getNombreCompleto(),
            request.getEmail(),
            request.getTelefono(),
            request.getTipoDocumento(),
            request.getNumeroDocumento(),
            request.getDireccion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Buscar por username o emailAcceso
        Optional<Usuario> opt = usuarioRepository
                .findByUsernameOrEmailAcceso(request.getIdentifier(), request.getIdentifier());

        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        Usuario u = opt.get();

        if (!u.getActivo())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario inactivo");

        if (!passwordEncoder.matches(request.getPassword(), u.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(token, u.getUsername(), u.getEmailAcceso(), roles));
    }
}