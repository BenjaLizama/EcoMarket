package com.ecomarket.autenticacionusuario.controller;


import com.ecomarket.autenticacionusuario.dto.*;
import com.ecomarket.autenticacionusuario.model.*;
import com.ecomarket.autenticacionusuario.service.*;
import com.ecomarket.autenticacionusuario.validator.CrearUsuarioDTOValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuario", description = "Operaciones relacionadas con el usuario.")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MetodoPagoService metodoPagoService;
    @Autowired
    private TarjetaService tarjetaService;
    @Autowired
    private TipoCuentaService tipoCuentaService;
    @Autowired
    private ProductoMCService productoMCService;
    @Autowired
    private CarritoMCService carritoMCService;


    @GetMapping
    @Operation(summary = "Obtener todos los usuarios.", description = "Obtiene un listado de todos los usuarios existentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
            @ApiResponse(responseCode = "204", description = "Operacion exitosa, pero sin contenido.")
    })
    public ResponseEntity<List<Usuario>> listarusUarios() {
        List<Usuario> usuarios = usuarioService.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }


    // BUSCAR USUARIO
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario.", description = "Obtiene un usuario segun el ID.")
    @Parameter(description = "ID del usuario.", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado."),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    //AGREGAR USUARIO
    @PostMapping("/agregar")
    @Operation(
            summary = "Crear usuario.",
            description = "Agrega un nuevo usuario al sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CrearUsuarioDTO.class)
                    )
            )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado con exito"),
        @ApiResponse(responseCode = "400", description = "Error al crar el usuario.")
    })
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
        carritoMCService.crearCarrito(nuevoUsuario.getId());
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
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO) {
        // Si no existe el usuario retornara 404.
        if (!usuarioService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Creamos una instancia llamada usuarioActualizado.
        Usuario usuarioActualizado = usuarioService.findById(id);

        if (!actualizarUsuarioDTO.getNombre().isEmpty()) { usuarioActualizado.setNombre(actualizarUsuarioDTO.getNombre()); }
        if (!actualizarUsuarioDTO.getApellido().isEmpty()) { usuarioActualizado.setApellido(actualizarUsuarioDTO.getApellido()); }
        if (!actualizarUsuarioDTO.getDireccion().isEmpty()) { usuarioActualizado.setDireccion(actualizarUsuarioDTO.getDireccion()); }

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

    // Producto - Cliente

    // Obtener productos desde el cliente.
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoMP>> obtenerProductos() {
        List<ProductoMP> productoMPList = productoMCService.obtenerProductos();
        return ResponseEntity.ok(productoMPList);
    }


    // Obtener listado de productos dentro del carrito del cliente.
    @GetMapping("/{idUsuario}/carrito")
    public ResponseEntity<List<ItemDTO>> listarProductosCarrito(@PathVariable Long idUsuario) {
        if (!usuarioService.existById(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ItemDTO> productosCarrito = carritoMCService.listarProductosCarrito(idUsuario);

        if (productosCarrito.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productosCarrito);
    }

}
