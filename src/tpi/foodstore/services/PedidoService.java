package tpi.foodstore.services;

import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.entities.Pedido;
import tpi.foodstore.entities.Producto;
import tpi.foodstore.entities.Usuario;
import tpi.foodstore.enums.Estado;
import tpi.foodstore.enums.FormaPago;
import tpi.foodstore.exceptions.EntidadNoEncontradaException;
import tpi.foodstore.exceptions.PedidoInvalidoException;
import tpi.foodstore.services.ProductoService;

public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();
    private ProductoService productoService;

    public PedidoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Pedido crearPedido(Usuario usuario, FormaPago formaPago) throws PedidoInvalidoException {
        if (usuario == null || usuario.isEliminado()) {
            throw new PedidoInvalidoException("Usuario invalido o inexistente.");
        }
        Pedido nuevoPedido = new Pedido(formaPago, usuario);
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    public void agregarProducto(
            Long pedidoId, Long productoId,
            int cantidad
    ) throws PedidoInvalidoException, EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(pedidoId);
        
        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new PedidoInvalidoException("El pedido ya no esta pendiente, no se pueden agregar productos.");
        }

        // buscar el producto
        Producto producto = productoService.buscarProductoPorId(productoId);
        
        // verificar stock
        if (producto.getStock() < cantidad) {
            throw new PedidoInvalidoException("Stock insuficiente. Disponible: " + producto.getStock());
        }
        
        // agregar al pedido
        pedido.addDetallePedido(cantidad, producto);
        
        // reducir el stock
        productoService.actualizarStock(productoId, -cantidad);
    }

    public Pedido buscarPorId(Long id) throws PedidoInvalidoException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new PedidoInvalidoException("Pedido no encontrado con el ID: " + id);
    }

    public void cambiarEstado(Long pedidoId, Estado nuevoEstado) throws PedidoInvalidoException {
        Pedido pedido = buscarPorId(pedidoId);
        
        if (pedido.getEstado() == Estado.TERMINADO || pedido.getEstado() == Estado.CANCELADO) {
            throw new PedidoInvalidoException("No se puede cambiar el estado de un pedido ya finalizado o cancelado.");
        }
        
        pedido.setEstado(nuevoEstado);
    }
    
    public void eliminarPedido(Long pedidoId) throws PedidoInvalidoException {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setEliminado(true);
    }
    
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidosActivos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                pedidosActivos.add(p);
            }
        }
        return pedidosActivos;
    }
}
