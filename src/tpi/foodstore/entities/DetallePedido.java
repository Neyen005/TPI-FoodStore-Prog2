/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.entities;

/**
 *
 * @author gaston
 */
public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    private Double calcularSubtotal() {
        return cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return String.format(
                "DetallePedido ID: %d | Producto: %s | Cantidad: %d | Subtotal: $%.2f",
                getId(),
                producto.getNombre(),
                cantidad,
                subtotal
        );
    }

}
