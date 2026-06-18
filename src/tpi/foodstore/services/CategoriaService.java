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

    public Categoria editarCategoria(Long id, String nombre, String descripcion) throws EntidadNoEncontradaException, CategoriaDuplicadaException {
        Categoria categoria = buscarCategoriaPorId(id);

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia.");
        }

        nombre = nombre.trim();
        descripcion = descripcion.trim();

        for (Categoria otraCategoria : categorias) {
            if (!otraCategoria.isEliminado() && otraCategoria.getId().equals(id) && otraCategoria.getNombre().equalsIgnoreCase(nombre)) {
                throw new CategoriaDuplicadaException("Ya existe una categoria con ese nombre.");
            }
        }
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoria;
    }

    public Categoria eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarCategoriaPorId(id);

        categoria.setEliminado(true);
        return categoria;
    }
}
