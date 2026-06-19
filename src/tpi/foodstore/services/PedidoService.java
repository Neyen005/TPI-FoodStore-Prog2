
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.entities.Pedido;
import tpi.foodstore.entities.Producto;
import tpi.foodstore.entities.Usuario;
import tpi.foodstore.enums.Estado;
import tpi.foodstore.enums.FormaPago;
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
        Pedido nuevoPedido = new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    public void agregarProducto(Long pedidoId, Long productoId, int cantidad) throws PedidoInvalidoException {
        Pedido pedido = buscarPorId(pedidoId);
        
        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new PedidoInvalidoException("El pedido ya no esta pendiente, no se pueden agregar productos.");
        }

        try {
            Producto producto = productoService.buscarPorId(productoId);
            productoService.descontarStock(productoId, cantidad);
            pedido.addDetallePedido(cantidad, producto);
        } catch (ProductoException e) {
            // si falla el stock o el producto, lo transformamos en un pedido invalido
            throw new PedidoInvalidoException("No se pudo agregar el producto: " + e.getMessage());
        }
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
}
