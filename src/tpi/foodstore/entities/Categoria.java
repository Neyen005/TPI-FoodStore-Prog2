/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gaston
 */
public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos;
 
    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    @Override
    public String toString() {

        return String.format(
                "Categoria:%nID: %s%nNombre: %s%nDescripcion: %s%nCantidad Productos: %d",
                getId(),
                nombre,
                descripcion,
                productos.size()
        );
    }

}
