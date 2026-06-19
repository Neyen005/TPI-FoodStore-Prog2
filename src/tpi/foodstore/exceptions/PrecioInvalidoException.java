/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi.foodstore.exceptions;

public class PrecioInvalidoException extends RuntimeException {
    public PrecioInvalidoException(String mensaje) {
        super(mensaje); 
    }
}