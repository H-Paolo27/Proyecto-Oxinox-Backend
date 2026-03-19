// ─────────────────────────────────────────────────────────────
// MovimientoInventarioMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.MovimientoInventario;
import com.osinox.osinox.entity.Producto;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.dto.MovimientoInventarioDto;
 
public class MovimientoInventarioMapper {
 
    public static MovimientoInventarioDto toDto(MovimientoInventario m) {
        if (m == null) return null;
        Long productoId = m.getProducto() == null ? null : m.getProducto().getId();
        String productoNombre = m.getProducto() == null ? null : m.getProducto().getNombre();
        Long usuarioId = m.getUsuario() == null ? null : m.getUsuario().getId();
        String usuarioUsername = m.getUsuario() == null ? null : m.getUsuario().getUsername();
 
        return new MovimientoInventarioDto(
            m.getId(),
            productoId,
            productoNombre,
            m.getTipo() == null ? null : m.getTipo().name(),
            m.getCantidad(),
            m.getStockAnterior(),
            m.getStockPosterior(),
            m.getMotivo(),
            m.getReferenciaTipo(),
            m.getReferenciaId(),
            usuarioId,
            usuarioUsername,
            m.getFecha()
        );
    }
 
    public static MovimientoInventario toEntity(MovimientoInventarioDto d) {
        if (d == null) return null;
        MovimientoInventario m = new MovimientoInventario();
        m.setId(d.getId());
        if (d.getProductoId() != null) {
            Producto producto = new Producto();
            producto.setId(d.getProductoId());
            m.setProducto(producto);
        }
        if (d.getTipo() != null)
            m.setTipo(MovimientoInventario.TipoMovimiento.valueOf(d.getTipo()));
        m.setCantidad(d.getCantidad());
        m.setMotivo(d.getMotivo());
        m.setReferenciaTipo(d.getReferenciaTipo());
        m.setReferenciaId(d.getReferenciaId());
        if (d.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(d.getUsuarioId());
            m.setUsuario(usuario);
        }
        return m;
    }
}