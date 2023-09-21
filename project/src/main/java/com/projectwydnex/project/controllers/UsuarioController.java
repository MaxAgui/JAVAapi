package com.projectwydnex.project.controllers;

import com.projectwydnex.project.models.Usuario;
import com.projectwydnex.project.service.UsuarioService;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/api/usuarios")
    public ResponseEntity<?> crearNuevoUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        // Lógica para crear el usuario y guardarlo en la base de datos
        ResponseEntity<?> responseEntity = validarCampos(bindingResult);

        if (responseEntity != null) {
            return responseEntity;
        }

        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    @GetMapping(value = "/api/usuarios")
    public List<Usuario> obtenerUsuarios(){
        return usuarioService.obtenerTodosUsuarios();
    }

    @PutMapping(path = "/api/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/api/usuarios/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuarioEstado(id);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> validarCampos(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            for (FieldError error: bindingResult.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errors));
        }

        return null;
    }

}
