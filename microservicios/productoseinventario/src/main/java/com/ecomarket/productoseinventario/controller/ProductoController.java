package com.ecomarket.productoseinventario.controller;


import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.ecomarket.productoseinventario.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {


    @Autowired
    private ProductoService productoService;
    @Autowired
    private StockService stockService;


    @GetMapping // Solicitud Get
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) { // Si la lista se encuentra vacía.
            return ResponseEntity.noContent().build(); // Retorna 204 (No Content)
        }
        return ResponseEntity.ok(productos); // Devuelve el listado de productos encontrados.
    }


    @GetMapping("/{id}") // Recibe 'id' en la ruta de la petición HTTP
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id); // Busca el producto con el ID especificado.
        if (producto == null) { // Si el producto no existe (es null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 (Not Found)
        }
        return ResponseEntity.ok(producto); // Retorna 200 (OK) si encuentra el producto.
    }


    // Agrega productos.
    @PostMapping("/agregar") // Solicitud Post.
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Stock nuevoStock = new Stock();
        nuevoStock.setCantidad(0);
        nuevoStock.setFecha_actualizacion(LocalDateTime.now()); // Agrega la fecha actual.
        stockService.save(nuevoStock);

        producto.setStock(nuevoStock);

        productoService.save(producto); // Guarda el nuevo producto en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(producto); // Retorna 201 (Created).
    }


    // Actualizar producto.
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        // Validar si existe el producto.
        if (!productoService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el producto actual y settear sus valores.
        Producto productoActual = productoService.findById(id);
        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());

        // Guardar el producto actualizado y retornar.
        Producto productoActualizado = productoService.save(productoActual);
        return ResponseEntity.ok(productoActualizado);
    }


    // Eliminar producto.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!productoService.existById(id)) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }

        Producto producto = productoService.findById(id);
        Long stockId = producto.getStock().getId();

        productoService.delete(id);     // Elimina el producto
        stockService.delete(stockId);   // Elimina el stock relacionado

        return ResponseEntity.noContent().build(); // 204 OK
    }


}
