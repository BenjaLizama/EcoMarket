package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.dto.ActivacionDTO;
import com.ecomarket.autenticacionusuario.dto.CrearUsuarioDTO;
import com.ecomarket.autenticacionusuario.dto.LoginDTO;
import com.ecomarket.autenticacionusuario.dto.PermisosDTO;
import com.ecomarket.autenticacionusuario.model.MetodoPago;
import com.ecomarket.autenticacionusuario.model.Tarjeta;
import com.ecomarket.autenticacionusuario.model.TipoCuenta;
import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.service.MetodoPagoService;
import com.ecomarket.autenticacionusuario.service.TarjetaService;
import com.ecomarket.autenticacionusuario.service.TipoCuentaService;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import com.ecomarket.autenticacionusuario.validator.CrearUsuarioDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private TipoCuentaService tipoCuentaService;


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

    //AGREGAR USUARIO
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarUsuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO) { // <= Modifique el retorno a ResponseEntity<?>
        List<String> listaErrores = CrearUsuarioDTOValidator.validarErrores(crearUsuarioDTO);

        if (!listaErrores.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errores", listaErrores));
        }

        if (usuarioService.existsByCorreo(crearUsuarioDTO.getCorreo())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errores", List.of("El correo se encuentra asociado a una cuenta.")));
        }

        Usuario nuevoUsuario = new Usuario();
        MetodoPago nuevoMetodoPago = new MetodoPago();

        nuevoMetodoPago.setUsuario(nuevoUsuario);
        nuevoUsuario.setMetodoPago(nuevoMetodoPago);

        nuevoUsuario.setTipoCuenta(tipoCuentaService.findById(1L)); // Tipo cuenta basica | Nivel: Usuario
        nuevoUsuario.setEstado(false); // Las nuevas cuentas requieren verificacion para ser activadas.

        nuevoUsuario.setCorreo(crearUsuarioDTO.getCorreo());
        nuevoUsuario.setClave(crearUsuarioDTO.getClave());
        nuevoUsuario.setNombre(crearUsuarioDTO.getNombre());
        nuevoUsuario.setApellido(crearUsuarioDTO.getApellido());
        nuevoUsuario.setDireccion(crearUsuarioDTO.getDireccion());

        usuarioService.save(nuevoUsuario); // Guardamos
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
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


    // Inicio de sesion | Esto se puede hacer con un @RequestBody usando una clase DTO para no exponer el correo y la clave.
    @GetMapping("/inicio sesion")
    public ResponseEntity<Usuario> iniciarSesion(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.findByCorreo(loginDTO.getCorreo());
        if (usuario == null || !usuario.getClave().equals(loginDTO.getClave())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o clave inválidos");
        }

        return ResponseEntity.ok(usuario);
    }


    // Activar o desactivar perfiles de usuario.
    @PutMapping("/{id}/activacion")
    public ResponseEntity<Usuario> activacion(@PathVariable Long id, @RequestBody ActivacionDTO activacionDTO) {
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioActual = usuarioService.findById(id);
        usuarioActual.setEstado(activacionDTO.getEstado());
        usuarioService.save(usuarioActual);

        return ResponseEntity.ok(usuarioActual);
    }


    // Listar usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> usuariosActivos() {
        if (usuarioService.usuariosActivos().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Usuario> lista = usuarioService.usuariosActivos();
        return ResponseEntity.ok(lista);
    }


    // Asignar permisos
    @PutMapping("/{id}/tipo")
    public ResponseEntity<Usuario> actualizarTipo(@PathVariable Long id, @RequestBody PermisosDTO permisosDTO) {
        if (!usuarioService.existById(id)) {
          return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.findById(id);
        TipoCuenta tipoCuenta = tipoCuentaService.findById(permisosDTO.getTipoCuenta());
        usuario.setTipoCuenta(tipoCuenta);
        usuarioService.save(usuario);
        return ResponseEntity.ok(usuario);
    }

}
