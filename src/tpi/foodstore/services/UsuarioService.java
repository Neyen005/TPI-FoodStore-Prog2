package tpi.foodstore.services;

import java.util.ArrayList;
import java.util.List;
import tpi.foodstore.entities.Usuario;
import tpi.foodstore.enums.Rol;
import tpi.foodstore.exceptions.EntidadNoEncontradaException;
import tpi.foodstore.exceptions.UsuarioDuplicadoException;

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    //LISTAR USUARIOS
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuariosDisponibles = new ArrayList<>();
        for (Usuario user : usuarios) {
            if (!user.isEliminado()) {
                usuariosDisponibles.add(user);
            }
        }
        return usuariosDisponibles;
    }

    //CREAR USUARIO
    public Usuario crearUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol)
            throws UsuarioDuplicadoException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio.");
        }

        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail no puede estar vacio.");
        }

        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El celular no puede estar vacio.");
        }

        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacia.");
        }

        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede estar vacio.");
        }

        nombre = nombre.trim();
        apellido = apellido.trim();
        mail = mail.trim();
        celular = celular.trim();
        contrasenia = contrasenia.trim();

        for (Usuario user : usuarios) {

            if (!user.isEliminado() && user.getMail().equalsIgnoreCase(mail)) {
                throw new UsuarioDuplicadoException("Ya existe un usuario con ese mail.");
            }
        }
        Usuario usuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(usuario);
        return usuario;
    }
//BUSCAR USUARIO

    public Usuario buscarUsuarioPorId(Long id) throws EntidadNoEncontradaException {

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado() && usuario.getId().equals(id)) {
                return usuario;
            }
        }
        throw new EntidadNoEncontradaException("No existe un usuario con ese ID.");
    }
//EDITAR USUARIO

    public Usuario editarUsuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) throws EntidadNoEncontradaException, UsuarioDuplicadoException {
        Usuario usuario = buscarUsuarioPorId(id);

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio.");
        }

        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail no puede estar vacio.");
        }

        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El celular no puede estar vacio.");
        }

        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacia.");
        }

        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede estar vacio.");
        }

        nombre = nombre.trim();
        apellido = apellido.trim();
        mail = mail.trim();
        celular = celular.trim();
        contrasenia = contrasenia.trim();

        for (Usuario otroUsuario : usuarios) {
            if (!otroUsuario.isEliminado() && !otroUsuario.getId().equals(id) && otroUsuario.getMail().equalsIgnoreCase(mail)) {
                throw new UsuarioDuplicadoException("Ya existe un usuario con ese mail.");
            }
        }
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol);
        
        return usuario;
    }
    //Eliminar Usuario

    public Usuario eliminarUsuario(Long id) throws UsuarioDuplicadoException, EntidadNoEncontradaException {
        Usuario usuario = buscarUsuarioPorId(id);

        usuario.setEliminado(true);
        return usuario;
    }
}
