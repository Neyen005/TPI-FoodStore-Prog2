/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.prog2.entities;

import examen.prog2.enums.Estado;
import examen.prog2.enums.FormaPago;
import examen.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;
    
    // Usamos un contador estático solo para simular autoincremento en memoria
    private static long idDetalleContador = 1;

    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formaPago) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido dp : detalles) {
            this.total += dp.getSubtotal();
        }
    }

    // Composicion: crea internamente el DetallePedido 
    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(idDetalleContador++, cantidad, producto);
        this.detalles.add(nuevoDetalle);
        calcularTotal(); // Se invoca automáticamente
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido aBorrar = findeDetallePedidoByProducto(producto);
        if (aBorrar != null) {
            this.detalles.remove(aBorrar);
            calcularTotal(); // Recalcula tras eliminar
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && !usuario.getPedidos().contains(this)) {
            usuario.agregarPedido(this);
        }
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public Usuario getUsuario() { return usuario; }

    @Override
    public String toString() {
        return String.format("Pedido #%d | Fecha: %s | Estado: %s | FormaPago: %s", 
                getId(), fecha.toString(), estado, formaPago);
    }
}
