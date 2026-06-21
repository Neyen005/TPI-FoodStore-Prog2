package tpi.foodstore;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import tpi.foodstore.entities.Categoria;
import tpi.foodstore.entities.DetallePedido;
import tpi.foodstore.entities.Pedido;
import tpi.foodstore.entities.Producto;
import tpi.foodstore.entities.Usuario;
import tpi.foodstore.enums.Estado;
import tpi.foodstore.enums.FormaPago;
import tpi.foodstore.enums.Rol;
import tpi.foodstore.exceptions.CategoriaDuplicadaException;
import tpi.foodstore.exceptions.EntidadNoEncontradaException;
import tpi.foodstore.exceptions.PedidoInvalidoException;
import tpi.foodstore.exceptions.UsuarioDuplicadoException;
import tpi.foodstore.services.CategoriaService;
import tpi.foodstore.services.PedidoService;
import tpi.foodstore.services.ProductoService;
import tpi.foodstore.services.UsuarioService;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService(categoriaService);
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService(productoService);

    public static void main(String[] args) throws Exception {
        int opcion = -1;
        
        while (opcion != 0) {
            System.out.println("\n _____               _   ____  _                 \n" +
"|  ___|__   ___   __| | / ___|| |_ ___  _ __ ___ \n" +
"| |_ / _ \\ / _ \\ / _` | \\___ \\| __/ _ \\| '__/ _ \\\n" +
"|  _| (_) | (_) | (_| |  ___) | || (_) | | |  __/\n" +
"|_|  \\___/ \\___/ \\__,_| |____/ \\__\\___/|_|  \\___|");
            System.out.println("\n1. Categorías");
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
                    // Primero mostrar categorías disponibles
                    System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");
                    listarCategoriasEnConsola();
                    System.out.println();

                    System.out.print("ID de la Categoría (ingrese el número que aparece en la columna ID): ");
                    Long categoriaId;
                    try {
                        categoriaId = Long.parseLong(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debe ingresar un número válido.");
                        break;
                    }

                    // Verificar que la categoría existe antes de continuar
                    try {
                        categoriaService.buscarCategoriaPorId(categoriaId);
                    } catch (EntidadNoEncontradaException e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("Primero debe crear una categoría antes de agregar productos.");
                        break;
                    }

                    System.out.print("Nombre del producto: ");
                    String nombre = scanner.nextLine();
                    double precio;
                    System.out.print("Precio: ");
                    try {
                        precio = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debe ingresar un número válido para el precio.");
                        break;
                    }
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Stock: ");
                    int stock;
                    try {
                        stock = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debe ingresar un número válido para el stock.");
                        break;
                    }
                    System.out.print("URL de imagen (opcional, presione Enter para omitir): ");
                    String imagen = scanner.nextLine();
                    if (imagen.trim().isEmpty()) {
                        imagen = "sin-imagen.jpg"; // Valor por defecto
                    }

                    productoService.crearProducto(nombre, precio, descripcion, stock, imagen, categoriaId);
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
        List<Usuario> lista = usuarioService.listarUsuarios();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay usuarios disponibles.");
        } else {
            System.out.println("\nListado de Usuarios:");
            System.out.printf("%-5s | %-20s | %-20s | %s%n", "ID", "Nombre", "Apellido", "Email");
            System.out.println("-------------------------------------------------------");
            for (Usuario u : lista) {
                System.out.printf("%-5d | %-20s | %-20s | %s%n", 
                    u.getId(), u.getNombre(), u.getApellido(), u.getMail());
            }
        }
    }

    private static void menuPedidos() throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- SUBMENÚ PEDIDOS ---");
            System.out.println("1. Listar todos los pedidos");
            System.out.println("2. Crear nuevo pedido");
            System.out.println("3. Ver detalle de un pedido");
            System.out.println("4. Agregar producto a un pedido");
            System.out.println("5. Cambiar estado de un pedido");
            System.out.println("6. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                listarPedidosEnConsola();
                break;
                case 2:
                    crearNuevoPedido();
                    break;
                case 3:
                    verDetallePedido();
                    break;
                case 4:
                    agregarProductoAPedido();
                    break;
                case 5:
                    cambiarEstadoPedido();
                    break;
                case 6:
                    eliminarPedido();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción fuera de rango.");
            }
        }
    }

    private static void listarPedidosEnConsola() {
        try {
            List<Pedido> lista = pedidoService.listarPedidos();
            if (lista.isEmpty()) {
                System.out.println("\n[ No hay pedidos registrados ]");
            } else {
                System.out.println("\n LISTADO DE PEDIDOS:");
                System.out.printf("%-5s | %-20s | %-12s | %-10s | %s%n", "ID", "Usuario", "Fecha", "Estado", "Total");
                System.out.println("─────────────────────────────────────────────────────────────────────────");
                for (Pedido p : lista) {
                    System.out.printf("%-5d | %-20s | %-12s | %-10s | $%.2f%n", 
                        p.getId(), 
                        p.getUsuario().getNombre() + " " + p.getUsuario().getApellido(), 
                        p.getFecha(),
                        p.getEstado(), 
                        p.getTotal()
                    );
                }
                System.out.println("─────────────────────────────────────────────────────────────────────────");
            }
        } catch (Exception e) {
            System.out.println(" Error al listar pedidos: " + e.getMessage());
        }
    }
    
    private static void crearNuevoPedido() {
        try {
            // 1. Mostrar usuarios disponibles
            System.out.println("\n--- USUARIOS DISPONIBLES ---");
            listarUsuariosEnConsola();
            System.out.println();

            System.out.print("ID del Usuario que realiza el pedido: ");
            Long usuarioId = Long.parseLong(scanner.nextLine());
            Usuario user = usuarioService.buscarUsuarioPorId(usuarioId);

            // 2. Seleccionar forma de pago
            System.out.println("\nSeleccione forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");
            System.out.print("Opción: ");
            int opcionPago = Integer.parseInt(scanner.nextLine());
            FormaPago formaPago;
            switch(opcionPago) {
                case 1: formaPago = FormaPago.TARJETA; break;
                case 2: formaPago = FormaPago.TRANSFERENCIA; break;
                default: formaPago = FormaPago.EFECTIVO;
            }

            // 3. Crear el pedido
            Pedido nuevoPedido = null;
            try {
                nuevoPedido = pedidoService.crearPedido(user, formaPago);
            } catch (PedidoInvalidoException e) {
                System.out.println(" Error al crear el pedido: " + e.getMessage());
                return;
            }

            System.out.println("\n Pedido creado con ID: " + nuevoPedido.getId());
            System.out.println(" Estado: " + nuevoPedido.getEstado());
            System.out.println(" Usuario: " + user.getNombre() + " " + user.getApellido());
            System.out.println(" Total: $" + nuevoPedido.getTotal());

            // 4. Preguntar si quiere agregar productos ahora
            System.out.print("\n¿Desea agregar productos al pedido? (S/N): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                // Mostrar productos disponibles
                System.out.println("\n PRODUCTOS DISPONIBLES:");
                listarProductosEnConsola();
                System.out.println();

                boolean continuar = true;
                while (continuar) {
                    System.out.print("ID del producto (0 para terminar): ");
                    Long productoId = Long.parseLong(scanner.nextLine());

                    if (productoId == 0) {
                        continuar = false;
                        break;
                    }

                    System.out.print("Cantidad: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());

                    try {
                        pedidoService.agregarProducto(nuevoPedido.getId(), productoId, cantidad);
                        System.out.println(" Producto agregado correctamente!");
                        System.out.println(" Total actual: $" + nuevoPedido.getTotal());
                    } catch (PedidoInvalidoException | EntidadNoEncontradaException e) {
                        System.out.println(" Error: " + e.getMessage());
                    }

                    System.out.print("\n¿Agregar otro producto? (S/N): ");
                    String continuarRespuesta = scanner.nextLine();
                    if (!continuarRespuesta.equalsIgnoreCase("S")) {
                        continuar = false;
                    }
                }

                System.out.println("\n PEDIDO FINALIZADO:");
                mostrarDetallePedido(nuevoPedido.getId());
            }

        } catch (NumberFormatException e) {
            System.out.println(" Error: Debe ingresar un número válido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarDetallePedido(Long pedidoId) throws PedidoInvalidoException {
        Pedido pedido = pedidoService.buscarPorId(pedidoId);

        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println(" PEDIDO #" + pedido.getId());
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println(" Usuario: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
        System.out.println(" Fecha: " + pedido.getFecha());
        System.out.println(" Estado: " + pedido.getEstado());
        System.out.println(" Forma de pago: " + pedido.getFormaPago());
        System.out.println("───────────────────────────────────────────────────");
        System.out.println(" DETALLE DE PRODUCTOS:");

        List<DetallePedido> detalles = pedido.getDetalles();
        if (detalles.isEmpty()) {
            System.out.println("  (Sin productos)");
        } else {
            System.out.printf("  %-5s %-25s %-10s %-12s%n", "Cant.", "Producto", "Precio", "Subtotal");
            System.out.println("  ──────────────────────────────────────────────────");
            for (DetallePedido detalle : detalles) {
                System.out.printf("  %-5d %-25s $%-9.2f $%-11.2f%n", 
                    detalle.getCantidad(),
                    detalle.getProducto().getNombre(),
                    detalle.getProducto().getPrecio(),
                    detalle.getSubtotal()
                );
            }
            System.out.println("  ──────────────────────────────────────────────────");
            System.out.printf("  %-42s $%-11.2f%n", "TOTAL:", pedido.getTotal());
        }
        System.out.println("═══════════════════════════════════════════════════");
    }

    private static void agregarProductoAPedido() {
        try {
            // Mostrar pedidos pendientes
            System.out.println("\n--- PEDIDOS PENDIENTES ---");
            List<Pedido> pedidosPendientes = new ArrayList<>();
            try {
                for (Pedido p : pedidoService.listarPedidos()) {
                    if (p.getEstado() == Estado.PENDIENTE) {
                        pedidosPendientes.add(p);
                    }
                }
            } catch (Exception e) {
                System.out.println(" Error al listar pedidos: " + e.getMessage());
                return;
            }

            if (pedidosPendientes.isEmpty()) {
                System.out.println("[ No hay pedidos pendientes para agregar productos ]");
                return;
            }

            // Mostrar pedidos pendientes con formato mejorado
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.printf("║ %-5s ║ %-25s ║ %-12s ║ %-10s ║%n", "ID", "Usuario", "Estado", "Total");
            System.out.println("═══════════════════════════════════════════════════════════════");
            for (Pedido p : pedidosPendientes) {
                System.out.printf("║ %-5d ║ %-25s ║ %-12s ║ $%-9.2f ║%n", 
                    p.getId(),
                    truncarTexto(p.getUsuario().getNombre() + " " + p.getUsuario().getApellido(), 25),
                    p.getEstado(),
                    p.getTotal()
                );
            }
            System.out.println("═══════════════════════════════════════════════════════════════");

            System.out.print("\n Ingrese el ID del pedido: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());

            // Verificar que el pedido existe y está pendiente
            Pedido pedido = pedidoService.buscarPorId(pedidoId);
            if (pedido.getEstado() != Estado.PENDIENTE) {
                System.out.println(" El pedido no está pendiente. Estado actual: " + pedido.getEstado());
                return;
            }

            System.out.println("\nPedido seleccionado: #" + pedidoId + " - " + 
                              pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
            System.out.println("Total actual: $" + pedido.getTotal());

            // Bucle para agregar múltiples productos
            boolean continuarAgregando = true;
            int productosAgregados = 0;

            while (continuarAgregando) {
                System.out.println("\n───────────────────────────────────────────────────────────");
                System.out.println("PRODUCTOS DISPONIBLES:");
                listarProductosEnConsola();
                System.out.println("───────────────────────────────────────────────────────────");

                System.out.println("\nProductos en el pedido actual (" + pedido.getDetalles().size() + " productos):");
                if (pedido.getDetalles().isEmpty()) {
                    System.out.println("  (Sin productos aún)");
                } else {
                    System.out.printf("  %-5s %-25s %-8s %-10s%n", "Cant.", "Producto", "Precio", "Subtotal");
                    System.out.println("  ──────────────────────────────────────────────");
                    for (DetallePedido detalle : pedido.getDetalles()) {
                        System.out.printf("  %-5d %-25s $%-7.2f $%-9.2f%n", 
                            detalle.getCantidad(),
                            truncarTexto(detalle.getProducto().getNombre(), 25),
                            detalle.getProducto().getPrecio(),
                            detalle.getSubtotal()
                        );
                    }
                    System.out.println("  ──────────────────────────────────────────────");
                    System.out.printf("  %-35s $%-9.2f%n", "TOTAL:", pedido.getTotal());
                }

                System.out.println("\n───────────────────────────────────────────────────────────");
                System.out.print(" ID del producto (0 para finalizar): ");
                Long productoId = Long.parseLong(scanner.nextLine());

                // Si el usuario ingresa 0, termina el bucle
                if (productoId == 0) {
                    if (productosAgregados == 0) {
                        System.out.println("\n No se agregó ningún producto. ¿Desea continuar? (S/N): ");
                        String respuesta = scanner.nextLine();
                        if (!respuesta.equalsIgnoreCase("S")) {
                            continuarAgregando = false;
                        }
                    } else {
                        continuarAgregando = false;
                    }
                    continue;
                }

                // Verificar que el producto existe
                Producto producto;
                try {
                    producto = productoService.buscarProductoPorId(productoId);
                } catch (EntidadNoEncontradaException e) {
                    System.out.println(" Error: " + e.getMessage());
                    System.out.println(" Presione Enter para continuar...");
                    scanner.nextLine();
                    continue;
                }

                // Mostrar información del producto seleccionado
                System.out.println("\n Producto seleccionado: " + producto.getNombre());
                System.out.println("   Precio: $" + producto.getPrecio());
                System.out.println("   Stock disponible: " + producto.getStock());

                System.out.print(" Cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());

                if (cantidad <= 0) {
                    System.out.println(" La cantidad debe ser mayor a 0.");
                    continue;
                }

                if (cantidad > producto.getStock()) {
                    System.out.println(" Stock insuficiente. Disponible: " + producto.getStock());
                    continue;
                }

                // Intentar agregar el producto
                try {
                    pedidoService.agregarProducto(pedidoId, productoId, cantidad);
                    productosAgregados++;
                    System.out.println("\n Producto agregado correctamente!");
                    System.out.println(" Total actualizado: $" + pedido.getTotal());

                    // Preguntar si quiere seguir agregando
                    System.out.print("\n ¿Agregar otro producto? (S/N): ");
                    String respuesta = scanner.nextLine();
                    if (!respuesta.equalsIgnoreCase("S")) {
                        continuarAgregando = false;
                    }

                } catch (PedidoInvalidoException e) {
                    System.out.println(" Error al agregar producto: " + e.getMessage());
                    System.out.println(" Presione Enter para continuar...");
                    scanner.nextLine();
                } catch (EntidadNoEncontradaException e) {
                    System.out.println(" Error: " + e.getMessage());
                    System.out.println(" Presione Enter para continuar...");
                    scanner.nextLine();
                }
            }

            // Mostrar el resumen final del pedido
            System.out.println("\n═══════════════════════════════════════════════════");
            System.out.println("- RESUMEN FINAL DEL PEDIDO #" + pedidoId);
            System.out.println("═══════════════════════════════════════════════════");
            mostrarDetallePedido(pedidoId);
            System.out.println("\nSe agregaron " + productosAgregados + " producto(s) al pedido.");

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        } catch (PedidoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String truncarTexto(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }

    private static void verDetallePedido() throws PedidoInvalidoException {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();

            if (pedidos.isEmpty()) {
                System.out.println("\n[ No hay pedidos registrados para mostrar ]");
                return;
            }
            listarPedidosEnConsola();
            System.out.print("\nIngrese el ID del pedido a ver: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());
            mostrarDetallePedido(pedidoId);

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    private static void cambiarEstadoPedido() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();

            if (pedidos.isEmpty()) {
                System.out.println("\n[ No hay pedidos registrados para mostrar ]");
                return;
            }
            listarPedidosEnConsola();
            System.out.print("\nIngrese el ID del pedido a modificar: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());

            Pedido pedido = pedidoService.buscarPorId(pedidoId);
            System.out.println("\n[-] Pedido actual:");
            System.out.println("  ID: " + pedido.getId());
            System.out.println("  Estado actual: " + pedido.getEstado());
            System.out.println("  Total: $" + pedido.getTotal());

            System.out.println("\nSeleccione nuevo estado:");
            System.out.println("1. CONFIRMADO");
            System.out.println("2. TERMINADO");
            System.out.println("3. CANCELADO");
            System.out.print("Opción: ");
            int opcionEstado = Integer.parseInt(scanner.nextLine());

            Estado nuevoEstado;
            switch(opcionEstado) {
                case 1: nuevoEstado = Estado.CONFIRMADO; break;
                case 2: nuevoEstado = Estado.TERMINADO; break;
                case 3: nuevoEstado = Estado.CANCELADO; break;
                default: 
                    System.out.println(" Opción inválida.");
                    return;
            }

            pedidoService.cambiarEstado(pedidoId, nuevoEstado);
            System.out.println(" Estado actualizado a: " + nuevoEstado);

        } catch (NumberFormatException e) {
            System.out.println(" Error: Debe ingresar un número válido.");
        } catch (PedidoInvalidoException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void eliminarPedido() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();

            if (pedidos.isEmpty()) {
                System.out.println("\n[ No hay pedidos registrados para mostrar ]");
                return;
            }
            listarPedidosEnConsola();
            System.out.print("\nIngrese el ID del pedido a eliminar: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());

            Pedido pedido = pedidoService.buscarPorId(pedidoId);
            System.out.println("\n- ¿Está seguro que desea eliminar el pedido #" + pedidoId + "?");
            System.out.println("   Usuario: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
            System.out.println("   Total: $" + pedido.getTotal());
            System.out.print("   (S/N): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                pedidoService.eliminarPedido(pedidoId);
                System.out.println(" Pedido eliminado correctamente.");
            } else {
                System.out.println(" Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println(" Error: Debe ingresar un número válido.");
        } catch (PedidoInvalidoException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
}