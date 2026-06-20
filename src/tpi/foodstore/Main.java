package tpi.foodstore;

import tpi.foodstore.services.PedidoService;
import java.util.Scanner;
import java.util.List;
import tpi.foodstore.entities.*;
import tpi.foodstore.enums.Rol;
import tpi.foodstore.services.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    //private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) throws Exception {
        int opcion = -1;
        
        while (opcion != 0) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema... ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción fuera de rango. Intente nuevamente.");
            }
        }
    }

    private static void menuCategorias() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- SUBMENÚ CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    listarCategoriasEnConsola();
                    break;
                case 2:
                    System.out.print("Nombre de la categoría: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    
                    categoriaService.crearCategoria(nombre, descripcion);
                    System.out.println("Éxito: Categoría creada correctamente.");
                    break;
                case 3:
                    listarCategoriasEnConsola();
                    System.out.print("\nIngrese el ID de la categoría a editar: ");
                    Long idEditar = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nueva descripción: ");
                    String nuevaDescripcion = scanner.nextLine();
                    
                    categoriaService.editarCategoria(idEditar, nuevoNombre, nuevaDescripcion);
                    System.out.println("Éxito: Categoría actualizada.");
                    break;
                case 4:
                    listarCategoriasEnConsola();
                    System.out.print("\nIngrese el ID de la categoría a eliminar: ");
                    Long idEliminar = Long.parseLong(scanner.nextLine());
                    
                    categoriaService.eliminarCategoria(idEliminar);
                    System.out.println("Éxito: Categoría eliminada.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción fuera de rango.");
            }
        }
    }

    private static void listarCategoriasEnConsola() {
        List<Categoria> lista = categoriaService.listarCategorias();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay categorías disponibles.");
        } else {
            System.out.println("\nListado de Categorías:");
            System.out.printf("%-5s | %-20s | %s%n", "ID", "Nombre", "Descripción");
            System.out.println("-------------------------------------------------------");
            for (Categoria c : lista) {
                System.out.printf("%-5d | %-20s | %s%n", c.getId(), c.getNombre(), c.getDescripcion());
            }
        }
    }

    // ==========================================
    // 2. SUBMENÚ PRODUCTOS
    // ==========================================
    private static void menuProductos() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- SUBMENÚ PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    listarProductosEnConsola();
                    break;
                case 2:
                    System.out.print("Nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    System.out.print("ID de la Categoría: ");
                    Long categoriaId = Long.parseLong(scanner.nextLine());
                    
                    // Ajustá los parámetros según las firmas exactas de tu ProductoService
                    productoService.crearProducto(nombre, precio, categoriaId);
                    System.out.println("Éxito: Producto creado correctamente.");
                    break;
                case 3:
                    listarProductosEnConsola();
                    System.out.print("\nIngrese el ID del producto a editar: ");
                    Long idEditar = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                    
                    productoService.editarProducto(idEditar, nuevoNombre, nuevoPrecio);
                    System.out.println("Éxito: Producto actualizado.");
                    break;
                case 4:
                    listarProductosEnConsola();
                    System.out.print("\nIngrese el ID del producto a eliminar: ");
                    Long idEliminar = Long.parseLong(scanner.nextLine());
                    
                    productoService.eliminarProducto(idEliminar);
                    System.out.println("Éxito: Producto eliminado.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción fuera de rango.");
            }
        }
    }

    private static void listarProductosEnConsola() {
        List<Producto> lista = productoService.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay productos disponibles.");
        } else {
            System.out.println("\nListado de Productos:");
            System.out.printf("%-5s | %-20s | %-10s%n", "ID", "Nombre", "Precio");
            System.out.println("----------------------------------------------");
            for (Producto p : lista) {
                System.out.printf("%-5d | %-20s | $%-10.2f%n", p.getId(), p.getNombre(), p.getPrecio());
            }
        }
    }

    private static void menuUsuarios() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- SUBMENÚ USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    listarUsuariosEnConsola();
                    break;
                case 2:
                    System.out.print("Nombre de usuario: ");
                    String username = scanner.nextLine();
                    System.out.println("Apellido de usuario:");
                    String apellido = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Celular: ");
                    String celular = scanner.nextLine();
                    System.out.println("Contraseña: ");
                    String contrasenia = scanner.nextLine();
                    System.out.println("Seleccione el rol del usuario:");
                    System.out.println("1. ADMIN");
                    System.out.println("2. CLIENTE");
                    System.out.print("Seleccione una opción (1 o 2): ");
                    int opcionRol = Integer.parseInt(scanner.nextLine());
                    Rol rol = (opcionRol == 1) ? Rol.ADMIN : Rol.CLIENTE;
                    
                    usuarioService.crearUsuario(username, apellido, email, celular, contrasenia, rol);
                    System.out.println("Éxito: Usuario creado correctamente.");
                    break;
                case 3:
                    listarUsuariosEnConsola();
                    System.out.print("\nIngrese el ID del usuario a editar: ");
                    Long idEditar = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo nombre de usuario: ");
                    String nuevoUsername = scanner.nextLine();
                    System.out.println("Nuevo apellido de usuario: ");
                    String nuevoApellido = scanner.nextLine();
                    System.out.print("Nuevo email: ");
                    String nuevoEmail = scanner.nextLine();
                    System.out.println("Nuevo celular: ");
                    String nuevoCelular = scanner.nextLine();
                    System.out.println("Nueva Contraseña: ");
                    String nuevaContrasenia = scanner.nextLine();
                    System.out.println("Seleccione el rol del usuario:");
                    System.out.println("1. ADMIN");
                    System.out.println("2. CLIENTE");
                    System.out.print("Seleccione una opción (1 o 2): ");
                    int opcionNuevoRol = Integer.parseInt(scanner.nextLine());
                    Rol nuevoRol = (opcionNuevoRol == 1) ? Rol.ADMIN : Rol.CLIENTE;
                    
                    
                    usuarioService.editarUsuario(idEditar, nuevoUsername, nuevoApellido, nuevoEmail, nuevoCelular, nuevaContrasenia, nuevoRol);
                    System.out.println("Éxito: Usuario actualizado.");
                    break;
                case 4:
                    listarUsuariosEnConsola();
                    System.out.print("\nIngrese el ID del usuario a eliminar: ");
                    Long idEliminar = Long.parseLong(scanner.nextLine());
                    
                    usuarioService.eliminarUsuario(idEliminar);
                    System.out.println("Éxito: Usuario eliminado.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción fuera de rango.");
            }
        }
    }

    private static void listarUsuariosEnConsola() {
        // Ajustá el método de listado y los getters según tu entidad Usuario
        List<Usuario> lista = usuarioService.listarUsuarios();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay usuarios disponibles.");
        } else {
            System.out.println("\nListado de Usuarios:");
            System.out.printf("%-5s | %-20s | %s%n", "ID", "Username", "Email");
            System.out.println("-------------------------------------------------------");
            for (Usuario u : lista) {
                System.out.printf("%-5d | %-20s | %s%n", u.getId(), u.getNombre(), u.getMail());
            }
        }
    }

    private static void menuPedidos() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- SUBMENÚ PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    listarPedidosEnConsola();
                    break;
                case 2:
                    System.out.print("ID del Usuario que realiza el pedido: ");
                    Long usuarioId = Long.parseLong(scanner.nextLine());
                    System.out.print("Observaciones/Detalles: ");
                    String observaciones = scanner.nextLine();
                    
                    PedidoService.crearPedido(usuarioId, observaciones);
                    System.out.println("Éxito: Pedido registrado correctamente.");
                    break;
                case 3:
                    listarPedidosEnConsola();
                    System.out.print("\nIngrese el ID del pedido a editar: ");
                    Long idEditar = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo estado o modificaciones: ");
                    String modificacion = scanner.nextLine();
                    
                    PedidoService.editarPedido(idEditar, modificacion);
                    System.out.println("Éxito: Pedido modificado.");
                    break;
                case 4:
                    listarPedidosEnConsola();
                    System.out.print("\nIngrese el ID del pedido a eliminar: ");
                    Long idEliminar = Long.parseLong(scanner.nextLine());
                    
                    PedidoService.eliminarPedido(idEliminar);
                    System.out.println("Éxito: Pedido cancelado/eliminado.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción fuera de rango.");
            }
        }
    }

    private static void listarPedidosEnConsola() {
        // Ajustá el método de listado y los getters según tu entidad Pedido
        List<Pedido> lista = pedidoService.listarPedidos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay pedidos registrados.");
        } else {
            System.out.println("\nListado de Pedidos:");
            System.out.printf("%-5s | %-15s | %s%n", "ID", "Usuario ID", "Detalles");
            System.out.println("-------------------------------------------------------");
            for (Pedido p : lista) {
                System.out.printf("%-5d | %-15d | %s%n", p.getId(), p.getUsuarioId(), p.getObservaciones());
            }
        }
    }
}