package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.model.MetodoPago;
import com.ecomarket.autenticacionusuario.model.Tarjeta;
import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.service.MetodoPagoService;
import com.ecomarket.autenticacionusuario.service.TarjetaService;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MetodoPagoService metodoPagoService;
    @Autowired
    private TarjetaService tarjetaService;


    @GetMapping
    public ResponseEntity<List<Usuario>> listarusUarios() {
        List<Usuario> usuarios = usuarioService.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }


    // BUSCAR USUARIO
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
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

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setUsuario(usuario);
        usuario.setMetodoPago(metodoPago);

        usuarioService.save(usuario); // Se guarda el usuario en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario); // <= Agregue esto (respuesta del servidor 201 (CREATED))
    }



    // BORRAR USUARIO
    @DeleteMapping("/{id}")
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


    // Obtener lista metodos de pago.
    @GetMapping("/{id}/metodo_pago")
    public ResponseEntity<Set<Tarjeta>> otenerMetodosPago(@PathVariable Long id) {
        // Valida si existe el usuario.
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Instanciamos al usuario
        Usuario usuario = usuarioService.findById(id);
        // Obtenemos su lisatdo de tarjetas.
        Set<Tarjeta> listaTarjetas = usuario.getMetodoPago().getTarjetaList();
        return ResponseEntity.ok(listaTarjetas);
    }


    // Agregar tarjeta
    @PostMapping("/{id}/metodo_pago/agregar")
    public ResponseEntity<Tarjeta> agregarTarjeta(@PathVariable Long id, @RequestBody Tarjeta tarjeta) {
        // Valida si existe el usuario.
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        tarjetaService.save(tarjeta);

        Usuario usuario = usuarioService.findById(id);
        MetodoPago metodoPago = usuario.getMetodoPago();

        metodoPago.getTarjetaList().add(tarjeta);
        metodoPagoService.save(metodoPago);
        usuarioService.save(usuario);

        return ResponseEntity.ok(tarjeta);
    }

}
