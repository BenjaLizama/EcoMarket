package com.ecomarket.carrito.controller;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.model.ProductoMSDTO;
import com.ecomarket.carrito.services.CarritoService;
import com.ecomarket.carrito.services.ProductoMSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag(name = "Carrito", description = "Operaciones relacionadas con la gestión del carrito y sus productos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private ProductoMSService productoMSService;


    // Crear carrito para usuario => Segun ID
    @PostMapping("/crear/{idUsuario}")
    @Operation(
            summary = "Crear carrito para un usuario",
            description = "Crea un nuevo carrito de compras vacío para el usuario especificado por su ID.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID del usuario que recibe el carrito", example = "5")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Carrito creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Carrito.class),
                            examples = @ExampleObject(
                                    name = "",
                                    summary = "",
                                    value = """
                        {
                          "id": 1,
                          "usuarioId": 5,
                          "productos": [],
                          "total": 0.0
                        }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "ID invalido o error de validacion")
    })

    public ResponseEntity<Carrito> crear(@PathVariable Long idUsuario) {


        try {
            Carrito nuevoCarrito = carritoService.crearCarrito(idUsuario);
            return ResponseEntity.ok(nuevoCarrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Obtener productos dentro del carrito.
    @GetMapping("/productos/{idUsuario}")
    @Operation(
            summary = "Obtener productos del carrito de un usuario",
            description = "Retorna una lista de ítems (productos) que están actualmente en el carrito del usuario.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID del usuario", example = "5")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Item.class)),
                            examples = @ExampleObject(
                                    name = "ItemsCarrito",
                                    summary = "Ejemplo de ítems en el carrito",
                                    value = """
                        [
                          {
                            "productoId": 1,
                            "nombre": "xxxx",
                            "cantidad":xx ,
                            "precioUnitario": xxxxx,
                            "subtotal":xxxxx 
                          }
                        ]
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    public ResponseEntity<List<Item>> obtenerProductosEnCarrito(@PathVariable Long idUsuario) {
        try {
            List<Item> itemList = carritoService.itemsCarrito(idUsuario);
            return ResponseEntity.ok(itemList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Obtener productos
    @GetMapping("/productos")
    @Operation(
            summary = "Obtener todos los productos disponibles",
            description = "Obtiene todos los productos disponibles desde el microservicio de productos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoMSDTO.class)),
                            examples = @ExampleObject(
                                    name = "ProductosEjemplo",
                                    summary = "Ejemplo de productos disponibles",
                                    value = """
                        [
                          {
                            "id": 1,
                            "nombre": "xxx",
                            "descripcion": "",
                            "precio": xxxxx 
                          }
                        ]
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Error al obtener productos")
    })
    public ResponseEntity<List<ProductoMSDTO>> obtenerProductos() {
        try {
            List<ProductoMSDTO> productos = productoMSService.obtenerProductos();
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Agregar producto al carrito
    @PutMapping("/productos/{idUsuario}/agregar/{idProducto}")
    @Operation(
            summary = "Agregar producto al carrito",
            description = "Agrega un producto al carrito del usuario especificado.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID del usuario", example = "5"),
                    @Parameter(name = "idProducto", description = "ID del producto a agregar", example = "101")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto agregado correctamente al carrito",
                    content = @Content(schema = @Schema(implementation = Carrito.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error al agregar producto")
    })
    public ResponseEntity<Carrito> agregarProducto(@PathVariable Long idUsuario, @PathVariable Long idProducto) {
        try {
            Carrito carrito = productoMSService.agregarProducto(idUsuario, idProducto);
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
