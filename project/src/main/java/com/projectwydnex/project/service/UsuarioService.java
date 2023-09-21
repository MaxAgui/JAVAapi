package com.projectwydnex.project.service;

import com.projectwydnex.project.models.Usuario;
import com.projectwydnex.project.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodosUsuarios(){
        List<Usuario> usuariosActivos = usuarioRepository.findAll().stream()
                .filter(Usuario::getEstado) // Filtra por estado activo
                .collect(Collectors.toList());
        usuariosActivos.sort(Comparator.comparingLong(Usuario::getId));
        return usuariosActivos;
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setEstado(true);
        return usuarioRepository.save(usuario);
    }
    public boolean existeUsuarioConCorreo(String correo) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);
        return usuarioOptional.isPresent();
    }

    public Usuario actualizarUsuario(Long id, Usuario nuevoUsuario) {
        Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findById(id);
        if (usuarioExistenteOptional.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOptional.get();
            if (nuevoUsuario.getNombre() != null) {
                usuarioExistente.setNombre(nuevoUsuario.getNombre());
            }
            if (nuevoUsuario.getSector() != null) {
                usuarioExistente.setSector(nuevoUsuario.getSector());
            }
            if (nuevoUsuario.getCorreo() != null) {
                usuarioExistente.setCorreo(nuevoUsuario.getCorreo());
            }
            if (nuevoUsuario.getRol() != null) {
                usuarioExistente.setRol(nuevoUsuario.getRol());
            }
            if (nuevoUsuario.getPassword() != null) {
                usuarioExistente.setPassword(nuevoUsuario.getPassword());
            }

            return usuarioRepository.save(usuarioExistente); // Guarda y devuelve el usuario actualizado
        } else {
            return null; // Usuario no encontrado, puedes manejar este caso según tus necesidades
        }
    }
    public Usuario actualizarUsuarioEstado(Long id) {
        Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findById(id);
        if (usuarioExistenteOptional.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOptional.get();
            usuarioExistente.setEstado(false);
            return usuarioRepository.save(usuarioExistente);
        } else {
            return null; // Usuario no encontrado, puedes manejar este caso según tus necesidades
        }
    }
}
