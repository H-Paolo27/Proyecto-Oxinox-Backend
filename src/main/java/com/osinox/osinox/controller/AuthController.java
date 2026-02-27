package com.osinox.osinox.controller;
import com.osinox.osinox.dto.JwtResponse;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.security.JwtUtil;
import com.osinox.osinox.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.osinox.osinox.dto.LoginRequest;
import com.osinox.osinox.dto.RegisterRequest;
import com.osinox.osinox.dto.UsuarioDto;
import com.osinox.osinox.mapper.UsuarioMapper;
import com.osinox.osinox.service.UsuarioService;
import com.osinox.osinox.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        Usuario usuario = usuarioService.registerUser(request.getUsername(), request.getPassword(), request.getRoleName());
        UsuarioDto dto = UsuarioMapper.toDto(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> opt = usuarioRepository.findByUsername(request.getUsername());
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        Usuario u = opt.get();
        if (!passwordEncoder.matches(request.getPassword(), u.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(token, u.getUsername(), roles));
    }
    
}
