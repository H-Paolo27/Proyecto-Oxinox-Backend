package com.osinox.osinox.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.repository.RolRepository;
import com.osinox.osinox.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registerUser(String username, String password, String roleName) {
        Rol rol = rolRepository.findByNombre(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRoles(Set.of(rol));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario updateUsername(Long id, String nuevoUsername) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setUsername(nuevoUsername);
        return usuarioRepository.save(u);
    }

    public void deleteUser(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuario no encontrado");
        usuarioRepository.deleteById(id);
    }

    public Usuario activar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setActivo(true);
        return usuarioRepository.save(u);
    }

    public Usuario desactivar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setActivo(false);
        return usuarioRepository.save(u);
    }

    public Usuario cambiarPassword(Long id, String nuevaPassword) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setPassword(passwordEncoder.encode(nuevaPassword));
        return usuarioRepository.save(u);
    }

    public Usuario asignarRol(Long id, Long rolId) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        u.getRoles().add(rol);
        return usuarioRepository.save(u);
    }

    public Usuario quitarRol(Long id, Long rolId) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.getRoles().removeIf(r -> r.getId().equals(rolId));
        return usuarioRepository.save(u);
    }
}