package com.osinox.osinox.service;

import org.springframework.stereotype.Service;

import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public Rol findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }
}