package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.model.DetallePago;
import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.repository.DetallePagoRepository;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DetallePagoRepository detallePagoRepository;


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
        // Validar si el correo esta asociado a una cuenta.
        for (Usuario u : usuarioService.findAll()) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }

        DetallePago detallePago = new DetallePago();
        detallePago.setUsuario(usuario);
        usuario.setDetallePago(detallePago);

        usuarioService.save(usuario); // Se guarda el usuario en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario); // <= Agregue esto (respuesta del servidor 201 (CREATED))
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
