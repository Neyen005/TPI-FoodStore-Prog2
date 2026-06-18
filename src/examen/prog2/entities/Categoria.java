/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.prog2.entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    // Bidireccionalidad 1 a N
    public void agregarProducto(Producto producto) {
        if (producto != null && !this.productos.contains(producto)) {
            this.productos.add(producto);
            if (producto.getCategoria() != this) {
                producto.setCategoria(this);
            }
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public List<Producto> getProductos() { return productos; }

    @Override
    public String toString() {
        return String.format("Categoria[id=%d, nombre='%s']", getId(), nombre);
    }
}
