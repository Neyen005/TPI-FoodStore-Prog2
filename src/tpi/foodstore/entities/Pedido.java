/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.enums.Estado;
import tpi.foodstore.enums.FormaPago;
import tpi.foodstore.interfaces.Calculable;

/**
 *
 * @author gaston
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    public Pedido(FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        this.usuario = usuario;
        this.total = 0.0;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(cantidad, producto);
        detalles.add(nuevoDetalle);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido eliminarDetalle = findDetallePedidoByProducto(producto);
        if (eliminarDetalle != null) {
            detalles.remove(eliminarDetalle);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido detalle : detalles) {
            this.total += detalle.getSubtotal();
        }
    }

    @Override
    public String toString() {

        return String.format(
                "ID: %d | Usuario: %s %s | Fecha: %s | Estado: %s | Forma de pago: %s | Total: $%.2f",
                getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                fecha,
                estado,
                formaPago,
                total
        );
    }

}
