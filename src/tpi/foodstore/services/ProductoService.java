/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.services;

import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.entities.Categoria;
import tpi.foodstore.entities.Producto;
import tpi.foodstore.exceptions.EntidadNoEncontradaException;
import tpi.foodstore.exceptions.PrecioInvalidoException;
import tpi.foodstore.exceptions.StockInvalidoException;

public class ProductoService {

    private List<Producto> productos;

    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    public Producto crearProducto(String nombre, Double precio, String descripcion, int stock, String imagen, Categoria categoria) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }

        // Ahora sí le podemos pasar el texto a tu excepción
        if (precio == null || precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo."); 
        }

        // Ahora sí le podemos pasar el texto a tu excepción
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo."); 
        }

        if (categoria == null || categoria.isEliminado()) {
            throw new EntidadNoEncontradaException("La categoria no existe o esta eliminada.");
        }

        nombre = nombre.trim();
        if (descripcion != null) {
            descripcion = descripcion.trim();
        }

        Producto producto = new Producto(nombre, precio, descripcion, stock, imagen, categoria);
        productos.add(producto);
        return producto;
    }

    public List<Producto> listarProductos() {
        List<Producto> productosDisponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                productosDisponibles.add(producto);
            }
        }
        return productosDisponibles;
    }

    public Producto buscarProductoPorId(Long id) {

        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.getId().equals(id)) {
                return producto;
            }
        }
        throw new EntidadNoEncontradaException("No existe producto con ese ID.");
    }

    public Producto editarProducto(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Categoria categoria) {
        Producto producto = buscarProductoPorId(id);

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (precio != null && precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo."); 
        }

        if (stock != null && stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo."); 
        }

        nombre = nombre.trim();
        if (descripcion != null) {
            descripcion = descripcion.trim();
        }

        producto.setNombre(nombre);
        
        if (precio != null) {
            producto.setPrecio(precio);
        }
        if (descripcion != null) {
            producto.setDescripcion(descripcion);
        }
        if (stock != null) {
            producto.setStock(stock);
        }
        if (imagen != null) {
            producto.setImagen(imagen);
        }
        if (categoria != null && !categoria.isEliminado()) {
            producto.setCategoria(categoria);
        }

        return producto;
    }

    public Producto eliminarProducto(Long id) {
        Producto producto = buscarProductoPorId(id);
        producto.setEliminado(true);
        return producto;
    }
}