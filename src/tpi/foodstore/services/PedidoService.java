public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();
    private ProductoService productoService;

    public PedidoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Pedido crearPedido(Usuario usuario, FormaPago formaPago) {
        if (usuario == null || usuario.isEliminado()) {
            throw new PedidoException("Usuario inválido.");
        }
        Pedido nuevoPedido = new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    public void agregarProducto(Long pedidoId, Long productoId, int cantidad) {
        Pedido pedido = buscarPorId(pedidoId);
        
        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new PedidoException("El pedido ya no está pendiente.");
        }

        Producto producto = productoService.buscarPorId(productoId);
        productoService.descontarStock(productoId, cantidad);

        pedido.addDetallePedido(cantidad, producto);
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new PedidoException("Pedido no encontrado.");
    }

    public void cambiarEstado(Long pedidoId, Estado nuevoEstado) {
        Pedido pedido = buscarPorId(pedidoId);
        
        if (pedido.getEstado() == Estado.TERMINADO || pedido.getEstado() == Estado.CANCELADO) {
            throw new PedidoException("No se puede modificar un pedido finalizado o cancelado.");
        }
        
        pedido.setEstado(nuevoEstado);
    }
}
