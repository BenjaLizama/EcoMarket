package com.ecomarket.productoseinventario.controller;


import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public void agregarProducto(String nombre, String descripcion, Integer precio) {
        Producto producto = new Producto();
        Stock stock = new Stock();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
    }

    @PostMapping
    public void agregarCategoria(String nombre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
    }

}
