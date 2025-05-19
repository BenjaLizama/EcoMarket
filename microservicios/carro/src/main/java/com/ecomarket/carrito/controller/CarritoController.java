package com.ecomarket.carrito.controller;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
