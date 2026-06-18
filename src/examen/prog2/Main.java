/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.prog2;

import examen.prog2.entities.*;
import examen.prog2.enums.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        // 1. Instanciamos 3 Categorías
        Categoria catBebidas = new Categoria(1L, "Bebidas", "Gaseosas y aguas");
        Categoria catSnacks = new Categoria(2L, "Snacks", "Papas y salados");
        Categoria catDulces = new Categoria(3L, "Dulces", "Chocolates y golosinas");

        // 2. Instanciamos 6 Productos (2 por categoría) y los asociamos
        Producto p1 = new Producto(1L, "Coca Cola 2L", 1500.0, "Gaseosa Cola", 100, "coca.jpg", true);
        p1.setCategoria(catBebidas);
        Producto p2 = new Producto(2L, "Agua Mineral 1L", 800.0, "Agua sin gas", 50, "agua.jpg", true);
        p2.setCategoria(catBebidas);
        
        Producto p3 = new Producto(3L, "Papas Lays", 1200.0, "Papas fritas clasicas", 40, "lays.jpg", true);
        p3.setCategoria(catSnacks);
        Producto p4 = new Producto(4L, "Doritos", 1300.0, "Snack de queso", 30, "doritos.jpg", true);
        p4.setCategoria(catSnacks);
        
        Producto p5 = new Producto(5L, "Alfajor Milka", 900.0, "Alfajor triple", 80, "milka.jpg", true);
        p5.setCategoria(catDulces);
        Producto p6 = new Producto(6L, "Chocolate Block", 1800.0, "Chocolate con mani", 20, "block.jpg", true);
        p6.setCategoria(catDulces);

        // 3. Instanciamos 2 Usuarios
        Usuario admin = new Usuario(1L, "Carlos", "Gomez", "carlos@admin.com", "112233", "pass", Rol.ADMIN);
        Usuario cliente = new Usuario(2L, "Ana", "Lopez", "ana@cliente.com", "445566", "pass", Rol.USUARIO);
        
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(admin);
        listaUsuarios.add(cliente);

        // 4 y 5. Instanciamos 4 Pedidos (2 por usuario) y 12 Detalles (3 por pedido)
        // Pedido 1 - Admin
        Pedido ped1 = new Pedido(1L, LocalDate.now(), Estado.CONFIRMADO, FormaPago.TARJETA);
        ped1.addDetallePedido(2, p1); // Subtotal: 3000
        ped1.addDetallePedido(1, p3); // Subtotal: 1200
        ped1.addDetallePedido(3, p5); // Subtotal: 2700
        admin.agregarPedido(ped1);    // Total: 6900
        
        // Pedido 2 - Admin
        Pedido ped2 = new Pedido(2L, LocalDate.now(), Estado.PENDIENTE, FormaPago.TRANSFERENCIA);
        ped2.addDetallePedido(1, p2); // Subtotal: 800
        ped2.addDetallePedido(2, p4); // Subtotal: 2600
        ped2.addDetallePedido(1, p6); // Subtotal: 1800
        admin.agregarPedido(ped2);    // Total: 5200

        // Pedido 3 - Cliente
        Pedido ped3 = new Pedido(3L, LocalDate.now(), Estado.TERMINADO, FormaPago.EFECTIVO);
        ped3.addDetallePedido(3, p1); // Subtotal: 4500
        ped3.addDetallePedido(2, p2); // Subtotal: 1600
        ped3.addDetallePedido(1, p3); // Subtotal: 1200
        cliente.agregarPedido(ped3);  // Total: 7300

        // Pedido 4 - Cliente
        Pedido ped4 = new Pedido(4L, LocalDate.now(), Estado.CANCELADO, FormaPago.TARJETA);
        ped4.addDetallePedido(1, p4); // Subtotal: 1300
        ped4.addDetallePedido(5, p5); // Subtotal: 4500
        ped4.addDetallePedido(2, p6); // Subtotal: 3600
        cliente.agregarPedido(ped4);  // Total: 9400

        // 6. Imprimir el Reporte Exacto solicitado por Consola
        for (Usuario u : listaUsuarios) {
            System.out.println("================================================================");
            System.out.println(u.toString());
            System.out.println("================================================================");
            
            double totalAcumuladoUsuario = 0.0;
            
            for (Pedido ped : u.getPedidos()) {
                System.out.println(ped.toString());
                for (DetallePedido det : ped.getDetalles()) {
                    System.out.println(det.toString());
                }
                System.out.println(String.format("TOTAL DEL PEDIDO: $%.2f", ped.getTotal()));
                System.out.println(ped.toString()); 
                totalAcumuladoUsuario += ped.getTotal();
            }
            System.out.println(String.format("TOTAL ACUMULADO del usuario: $%.2f", totalAcumuladoUsuario));
            System.out.println("================================================================\n");
        }
    }
}
