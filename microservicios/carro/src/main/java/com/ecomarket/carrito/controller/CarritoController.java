package com.ecomarket.carrito.controller;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.model.ProductoMSDTO;
import com.ecomarket.carrito.services.CarritoService;
import com.ecomarket.carrito.services.ProductoMSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag(name = "Carrito", description =" operaciones relacionadas al Carrito de compras")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private ProductoMSService productoMSService;


    // Crear carrito para usuario => Segun ID
    @PostMapping("/crear/{idUsuario}")
    @Operation(summary = "crear Carrito para usuario segun ID", description = "crea Carrito para el usuario segun el ID")
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
    @Tag(name = "productos", description =" operacion relacionada producto del carrito")
    @Operation(summary = "obtener producto", description = "obtener uno o varios productos dentro del carrito")
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
    public ResponseEntity<Carrito> agregarProducto(@PathVariable Long idUsuario, @PathVariable Long idProducto) {
        try {
            Carrito carrito = productoMSService.agregarProducto(idUsuario, idProducto);
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
