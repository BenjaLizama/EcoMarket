package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")

public class ControladorUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarusUarios() {
        List<Usuario> usuarios = usuarioService.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    // BUSCAR USUARIO
    @GetMapping("{id}")
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    //AGREGAR USUARIO | Benja: Aqui te dejo algunas modificaciones.
    @PostMapping("/agregar")
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) { // <= Modifique el retorno a ResponseEntity<Usuario>

        usuarioService.save(usuario); // Se guarda el usuario en la base de datos.
        return ResponseEntity.ok(usuario); // <= Agregue esto (respuesta del servidor 200 (OK))
    }



    // BORRAR USUARIO
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioService.findById(id);
        usuarioService.delete(usuario.getId());
        return ResponseEntity.noContent().build();
    }

    // Actualizar usuario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Si no existe el usuario retornara 404.
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Creamos una instancia llamada usuarioActualizado.
        Usuario usuarioActualizado = usuarioService.findById(id);

        // Validamos si el campo del RequestBody no es null y si tampoco esta vacio para settear la nueva informacion.
        // Esto permite actualizar solo los campos especificos, sin necesidad de reinsertar campos que no queremos actualizar.
        if (usuario.getCorreo() != null && !usuario.getCorreo().isBlank()) { usuarioActualizado.setCorreo(usuario.getCorreo()); }
        if (usuario.getNombre() != null && !usuario.getNombre().isBlank()) { usuarioActualizado.setNombre(usuario.getNombre()); }
        if (usuario.getClave() != null && !usuario.getClave().isBlank()) { usuarioActualizado.setClave(usuario.getClave()); }
        if (usuario.getDireccion() != null && !usuario.getDireccion().isBlank()) { usuarioActualizado.setDireccion(usuario.getDireccion()); }

        // Guardamos usuarioActualizado.
        usuarioService.save(usuarioActualizado);
        return ResponseEntity.ok(usuarioActualizado);
    }

}
