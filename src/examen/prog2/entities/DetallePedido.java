/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.prog2.entities;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal(); // Calcula automáticamente
    }

    // Encapsulamiento 
    private Double calcularSubtotal() {
        if (producto != null && producto.getPrecio() != null) {
            return cantidad * producto.getPrecio();
        }
        return 0.0;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal(); // Recalcula si cambia cantidad 
    }

    public int getCantidad() { return cantidad; }
    public Double getSubtotal() { return subtotal; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        this.subtotal = calcularSubtotal();
    }

    @Override
    public String toString() {
        return String.format("DetallePedido #%d: %s x %d => Subtotal: $%.2f", 
                getId(), producto.getNombre(), cantidad, subtotal);
    }
}
