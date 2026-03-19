package com.osinox.osinox.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.osinox.osinox.dto.CategoriaProductoDto;
import com.osinox.osinox.entity.CategoriaProducto;
import com.osinox.osinox.mapper.CategoriaProductoMapper;
import com.osinox.osinox.repository.CategoriaProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProductoDto> getAllCategorias() {
        return categoriaProductoRepository.findAll().stream()
                .map(CategoriaProductoMapper::toDto)
                .toList();
    }

    public CategoriaProductoDto getCategoriaById(Long id) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria de producto no encontrada"));
        return CategoriaProductoMapper.toDto(categoria);
    }

    public CategoriaProductoDto getCategoriaByNombre(String nombre) {
        CategoriaProducto categoria = categoriaProductoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return CategoriaProductoMapper.toDto(categoria);
    }

    public CategoriaProductoDto saveCategoria(CategoriaProductoDto dto) {
        CategoriaProducto entity = CategoriaProductoMapper.toEntity(dto);
        CategoriaProducto saved = categoriaProductoRepository.save(entity);
        return CategoriaProductoMapper.toDto(saved);
    }

    public CategoriaProductoDto updateCategoria(Long id, CategoriaProductoDto dto) {
        if (!categoriaProductoRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        CategoriaProducto entity = CategoriaProductoMapper.toEntity(dto);
        entity.setId(id);
        CategoriaProducto saved = categoriaProductoRepository.save(entity);
        return CategoriaProductoMapper.toDto(saved);
    }

    public void deleteCategoria(Long id) {
        if (!categoriaProductoRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        categoriaProductoRepository.deleteById(id);
    }
}

