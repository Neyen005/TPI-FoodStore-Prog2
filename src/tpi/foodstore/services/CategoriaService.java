/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.services;

import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.entities.Categoria;
import tpi.foodstore.exceptions.CategoriaDuplicadaException;
import tpi.foodstore.exceptions.EntidadNoEncontradaException;

/**
 *
 * @author gaston
 */
public class CategoriaService {

    private List<Categoria> categorias;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    
    public Categoria crearCategoria(String nombre, String descripcion)
            throws CategoriaDuplicadaException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia.");
        }

        nombre = nombre.trim();
        descripcion = descripcion.trim();

        for (Categoria categoria : categorias) {

            if (!categoria.isEliminado() && categoria.getNombre().equalsIgnoreCase(nombre)) {
                throw new CategoriaDuplicadaException("Ya existe una categoria con ese nombre.");
            }
        }

        Categoria categoria = new Categoria(nombre, descripcion);
        categorias.add(categoria);
        return categoria;
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> categoriasDisponibles = new ArrayList<>();
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                categoriasDisponibles.add(categoria);
            }
        }
        return categoriasDisponibles;
    }

    public Categoria buscarCategoriaPorId(Long id) throws EntidadNoEncontradaException {

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado() && categoria.getId().equals(id)) {
                return categoria;
            }
        }
        throw new EntidadNoEncontradaException("No existe categoria con ese ID.");
    }

    public Categoria editarCategoria(
            Long id, 
            String nombre, 
            String descripcion
    ) throws EntidadNoEncontradaException, CategoriaDuplicadaException {
        Categoria categoria = buscarCategoriaPorId(id);

        // validar y actualizar nombre si se proporciona
        if (nombre != null) {
            String nombreTrimmed = nombre.trim();
            if (nombreTrimmed.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }

            // verificar duplicados SOLO si el nombre cambio
            if (!nombreTrimmed.equalsIgnoreCase(categoria.getNombre())) {
                for (Categoria otraCategoria : categorias) {
                    if (!otraCategoria.isEliminado() 
                        && !otraCategoria.getId().equals(id)
                        && otraCategoria.getNombre().equalsIgnoreCase(nombreTrimmed)) {
                        throw new CategoriaDuplicadaException("Ya existe una categoría con ese nombre.");
                    }
                }
                categoria.setNombre(nombreTrimmed);
            }
        }

        // validar y actualizar descripcion si se proporciona
        if (descripcion != null) {
            String descripcionTrimmed = descripcion.trim();
            if (descripcionTrimmed.isEmpty()) {
                throw new IllegalArgumentException("La descripción no puede estar vacía.");
            }
            categoria.setDescripcion(descripcionTrimmed);
        }
        return categoria;
    }

    public Categoria eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarCategoriaPorId(id);

        categoria.setEliminado(true);
        return categoria;
    }
}
