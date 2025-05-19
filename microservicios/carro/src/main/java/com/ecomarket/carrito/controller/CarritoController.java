package com.ecomarket.carrito.controller;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;


    // Crear carrito para usuario => Segun ID
    @PostMapping("/crear/{idUsuario}")
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
    public ResponseEntity<List<Item>> obtenerProductosEnCarrito(@PathVariable Long idUsario) {
        try {
            List<Item> itemList = carritoService.itemsCarrito(idUsario);
            return ResponseEntity.ok(itemList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
