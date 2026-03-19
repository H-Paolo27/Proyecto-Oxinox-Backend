package com.osinox.osinox.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.osinox.osinox.dto.ProductoDto;
import com.osinox.osinox.dto.ProductoPublicoDTO;
import com.osinox.osinox.dto.ProductoRequestDTO;
import com.osinox.osinox.entity.CategoriaProducto;
import com.osinox.osinox.entity.Producto;
import com.osinox.osinox.mapper.ProductoMapper;
import com.osinox.osinox.repository.CategoriaProductoRepository;
import com.osinox.osinox.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaRepository;

    // ─────────────────────────────────────────────
    // MÉTODOS QUE YA TENÍAS — sin cambios
    // ─────────────────────────────────────────────

    public List<ProductoDto> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toDto)
                .toList();
    }

    public ProductoDto getProductoById(Long id) {
        return ProductoMapper.toDto(findOrThrow(id));
    }

    public ProductoDto getProductoByCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductoMapper.toDto(producto);
    }

    public ProductoDto saveProducto(ProductoDto dto) {
        Producto entity = ProductoMapper.toEntity(dto);
        Producto saved = productoRepository.save(entity);
        return ProductoMapper.toDto(saved);
    }

    public ProductoDto updateProducto(Long id, ProductoDto dto) {
        if (!productoRepository.existsById(id))
            throw new RuntimeException("Producto no encontrado");
        Producto entity = ProductoMapper.toEntity(dto);
        entity.setId(id);
        return ProductoMapper.toDto(productoRepository.save(entity));
    }

    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id))
            throw new RuntimeException("Producto no encontrado");
        productoRepository.deleteById(id);
    }

    public List<ProductoDto> getProductosBajoStock() {
        return productoRepository.findProductosBajoStockMinimo().stream()
                .map(ProductoMapper::toDto)
                .toList();
    }

    public List<ProductoDto> getByCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId).stream()
                .map(ProductoMapper::toDto)
                .toList();
    }

    // ─────────────────────────────────────────────
    // MÉTODOS NUEVOS — admin
    // ─────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ProductoDto> buscarAdmin(String nombre, Long idCategoria, Boolean estado) {
        return productoRepository.buscarAdmin(nombre, idCategoria, estado)
                .stream().map(ProductoMapper::toDto).toList();
    }

    @Transactional
    public ProductoDto crearDesdeRequest(ProductoRequestDTO dto) {
        if (productoRepository.existsByCodigo(dto.getCodigo()))
            throw new RuntimeException("Ya existe un producto con el código: " + dto.getCodigo());

        Producto p = new Producto();
        p.setCodigo(dto.getCodigo());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setUnidadMedida(dto.getUnidadMedida());
        p.setStockMinimo(dto.getStockMinimo());
        p.setPrecioUnitario(dto.getPrecioUnitario());
        p.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        p.setCategoria(resolverCategoria(dto.getIdCategoria()));

        return ProductoMapper.toDto(productoRepository.save(p));
    }

    @Transactional
    public ProductoDto actualizarDesdeRequest(Long id, ProductoRequestDTO dto) {
        Producto p = findOrThrow(id);

        if (!p.getCodigo().equals(dto.getCodigo())
                && productoRepository.existsByCodigo(dto.getCodigo()))
            throw new RuntimeException("Ya existe un producto con el código: " + dto.getCodigo());

        p.setCodigo(dto.getCodigo());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setUnidadMedida(dto.getUnidadMedida());
        p.setStockMinimo(dto.getStockMinimo());
        p.setPrecioUnitario(dto.getPrecioUnitario());
        if (dto.getEstado() != null) p.setEstado(dto.getEstado());
        p.setCategoria(resolverCategoria(dto.getIdCategoria()));

        return ProductoMapper.toDto(productoRepository.save(p));
    }

    @Transactional
    public void desactivar(Long id) {
        Producto p = findOrThrow(id);
        p.setEstado(false);
        productoRepository.save(p);
        // Soft delete — integridad referencial con cotizaciones, órdenes, etc.
    }

    // ─────────────────────────────────────────────
    // MÉTODOS NUEVOS — público (clientes)
    // ─────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ProductoPublicoDTO> listarPublicos(String nombre, Long idCategoria) {
        return productoRepository.buscarActivos(nombre, idCategoria)
                .stream().map(this::toPublicoDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProductoPublicoDTO obtenerPublicoPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .filter(prod -> Boolean.TRUE.equals(prod.getEstado()))
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toPublicoDTO(p);
    }

    // ─────────────────────────────────────────────
    // UTILS PRIVADOS
    // ─────────────────────────────────────────────

    private Producto findOrThrow(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    private CategoriaProducto resolverCategoria(Long idCategoria) {
        if (idCategoria == null) return null;
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + idCategoria));
    }

    private ProductoPublicoDTO toPublicoDTO(Producto p) {
        return ProductoPublicoDTO.builder()
                .idProducto(p.getId())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .categoriaNombre(p.getCategoria() != null ? p.getCategoria().getNombre() : null)
                .unidadMedida(p.getUnidadMedida())
                .imagenUrl(p.getImagenUrl())
                .build();
    }
    @Transactional
    public ProductoDto actualizarImagenUrl(Long id, String url) {
        Producto p = findOrThrow(id);
        p.setImagenUrl(url);
        return ProductoMapper.toDto(productoRepository.save(p));
    }
}