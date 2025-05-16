package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ControladorUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarusUarios() {
        return usuarioService.findAll();
    }
    // BUSCAR USUARIO
    @GetMapping("{id}")
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioService.findById(id);
    }
    //AGREGAR USUARIO
    @PostMapping
    public Usuario agregarUsuario(@RequestBody Usuario Usuario) {
        return usuarioService.save(Usuario);
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

    //@PutMapping("{id}") IMPLEMENTAR ACTUALIZAR USUARIO



}
