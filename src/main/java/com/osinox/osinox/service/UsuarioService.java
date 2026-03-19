package com.osinox.osinox.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.repository.PersonaRepository;
import com.osinox.osinox.repository.RolRepository;
import com.osinox.osinox.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonaRepository personaRepository;

    // ── Registro público de clientes ──────────────────────────
    public Usuario registerCliente(String username, String password,
                                   String nombreCompleto, String email,
                                   String telefono, String tipoDocumento,
                                   String numeroDocumento, String direccion) {
        Rol rol = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        Persona persona = new Persona();
        persona.setNombreRazonSocial(nombreCompleto);
        persona.setEmail(email);                      // email personal
        persona.setTelefono(telefono);
        persona.setDireccion(direccion);
        persona.setTipoPersona(Persona.TipoPersona.NATURAL);
        persona.setTipoRelacion(Persona.TipoRelacion.CLIENTE);
        if (tipoDocumento != null && !tipoDocumento.isBlank()) {
            try {
                persona.setTipoDocumento(Persona.TipoDocumento.valueOf(tipoDocumento));
            } catch (IllegalArgumentException ignored) {}
        }
        if (numeroDocumento != null && !numeroDocumento.isBlank())
            persona.setNumeroDocumento(numeroDocumento);

        persona = personaRepository.save(persona);

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmailAcceso(email);                // email para login
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setPersona(persona);
        usuario.setRoles(Set.of(rol));
        return usuarioRepository.save(usuario);
    }

    // ── Creación interna por admin ────────────────────────────
    public Usuario registerInterno(String username, String emailAcceso,
                                   String password, String roleName,
                                   String nombreCompleto, String telefono,
                                   String tipoDocumento, String numeroDocumento) {
        Rol rol = rolRepository.findByNombre(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));

        Persona persona = new Persona();
        persona.setNombreRazonSocial(nombreCompleto);
        persona.setTelefono(telefono);
        persona.setTipoPersona(Persona.TipoPersona.NATURAL);
        persona.setTipoRelacion(Persona.TipoRelacion.TRABAJADOR_INTERNO);
        if (tipoDocumento != null && !tipoDocumento.isBlank()) {
            try {
                persona.setTipoDocumento(Persona.TipoDocumento.valueOf(tipoDocumento));
            } catch (IllegalArgumentException ignored) {}
        }
        if (numeroDocumento != null && !numeroDocumento.isBlank())
            persona.setNumeroDocumento(numeroDocumento);

        persona = personaRepository.save(persona);

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmailAcceso(emailAcceso);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setPersona(persona);
        usuario.setRoles(Set.of(rol));
        return usuarioRepository.save(usuario);
    }

    // ── Consultas ─────────────────────────────────────────────
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ── Actualización ─────────────────────────────────────────
    public Usuario updateUsername(Long id, String nuevoUsername) {
        Usuario u = getUserById(id);
        u.setUsername(nuevoUsername);
        return usuarioRepository.save(u);
    }

    public Usuario updateEmailAcceso(Long id, String nuevoEmail) {
        Usuario u = getUserById(id);
        u.setEmailAcceso(nuevoEmail);
        return usuarioRepository.save(u);
    }

    public Usuario cambiarPassword(Long id, String nuevaPassword) {
        Usuario u = getUserById(id);
        u.setPassword(passwordEncoder.encode(nuevaPassword));
        return usuarioRepository.save(u);
    }

    // ── Estado ────────────────────────────────────────────────
    public Usuario activar(Long id) {
        Usuario u = getUserById(id);
        u.setActivo(true);
        return usuarioRepository.save(u);
    }

    public Usuario desactivar(Long id) {
        Usuario u = getUserById(id);
        u.setActivo(false);
        return usuarioRepository.save(u);
    }

    // ── Roles ─────────────────────────────────────────────────
    public Usuario asignarRol(Long id, Long rolId) {
        Usuario u = getUserById(id);
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        u.getRoles().add(rol);
        return usuarioRepository.save(u);
    }

    public Usuario quitarRol(Long id, Long rolId) {
        Usuario u = getUserById(id);
        u.getRoles().removeIf(r -> r.getId().equals(rolId));
        return usuarioRepository.save(u);
    }

    // ── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteUser(Long id) {
        Usuario u = getUserById(id);
        Long personaId = u.getPersona() != null ? u.getPersona().getId() : null;
        usuarioRepository.deleteRolesByUsuarioId(id);
        usuarioRepository.deleteUsuarioById(id);
        if (personaId != null)
            personaRepository.deletePersonaById(personaId);
    }
}