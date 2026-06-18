/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.entities;

import java.time.LocalDateTime;

/**
 *
 * @author gaston
 */
public abstract class Base {
    
    private static Long contadorId = 0L;
    private final Long id;
    private boolean eliminado;
    private final LocalDateTime createdAt;  

    public Base() {
        this.id = ++contadorId;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;  
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    
    @Override
    public abstract String toString();

}
    

