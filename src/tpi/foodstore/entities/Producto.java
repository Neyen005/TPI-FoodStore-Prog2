/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.entities;

/**
 *
 * @author gaston
 */
public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = stock > 0;
        this.categoria = categoria;
    }
    

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setPrecio(Double precio) {       
        this.precio = precio;    
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setStock(int stock) {
        if (stock > 0) {
            this.disponible = true;
        } else {
            this.disponible = false;
        }
        this.stock = stock;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }  
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

 
    @Override
    public String toString() {
        return String.format("Producto:%nID: %s%nNombre: %s%nPrecio: %.2f%nDescripcion: %s%nStock: %d%nImagen: %s%nDisponibilidad: %s%nCategoria: %s",
                getId(),
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria.getNombre()
        );
    }

}
