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
    private CategoriaService categoriaService;

    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.categoriaService = categoriaService;
    }
    
    // Metodo para crear producto con ID de categoria
    public Producto crearProducto(
            String nombre, 
            Double precio, 
            String descripcion, 
            int stock, 
            String imagen, 
            Long categoriaId
    ) {
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(categoriaId);
            return crearProducto(nombre, precio, descripcion, stock, imagen, categoria);
        } catch (EntidadNoEncontradaException e) {
            // Mostrar las categorias disponibles para ayudar
            List<Categoria> categoriasDisponibles = categoriaService.listarCategorias();
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("No existe categoría con ID: ").append(categoriaId);
            if (categoriasDisponibles.isEmpty()) {
                mensaje.append(". No hay categorías disponibles. Primero cree una categoría.");
            } else {
                mensaje.append(". Categorías disponibles (ID - Nombre): ");
                for (Categoria c : categoriasDisponibles) {
                    mensaje.append(c.getId()).append(" - ").append(c.getNombre()).append(", ");
                }
                mensaje.delete(mensaje.length() - 2, mensaje.length()); // eliminar ultima coma
            }
            throw new EntidadNoEncontradaException(mensaje.toString());
        }
    }

    public Producto crearProducto(
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            Categoria categoria
    ) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }

        if (precio == null || precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo."); 
        }
        
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

    // metodo para editar producto con parametros simplificados
    public Producto editarProducto(Long id, String nombre, Double precio) {
        Producto producto = buscarProductoPorId(id);

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (precio != null && precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo.");
        }

        producto.setNombre(nombre.trim());
        if (precio != null) {
            producto.setPrecio(precio);
        }

        return producto;
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
    
    /**
     * Actualiza el stock de un producto
     * @param id ID del producto
     * @param cantidad Cantidad a sumar (positivo) o restar (negativo)
     * @return El producto actualizado
     */
    public Producto actualizarStock(Long id, int cantidad) {
        Producto producto = buscarProductoPorId(id);
        int nuevoStock = producto.getStock() + cantidad;
        
        if (nuevoStock < 0) {
            throw new StockInvalidoException("Stock insuficiente. Stock actual: " + producto.getStock() + 
                                           ", intentando restar: " + Math.abs(cantidad));
        }
        
        producto.setStock(nuevoStock);
        return producto;
    }
}
