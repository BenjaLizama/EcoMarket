package com.ecomarket.productoseinventario.controller;


import com.ecomarket.productoseinventario.dto.ActualizarCategoriaDTO;
import com.ecomarket.productoseinventario.dto.ActualizarProductoDTO;
import com.ecomarket.productoseinventario.dto.BuscarDTO;
import com.ecomarket.productoseinventario.dto.StockDTO;
import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.CategoriaService;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.ecomarket.productoseinventario.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {


    @Autowired
    private ProductoService productoService;
    @Autowired
    private StockService stockService;
    @Autowired
    private CategoriaService categoriaService;


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
        producto.setCategoria(categoriaService.findById(1L).get());

        productoService.save(producto); // Guarda el nuevo producto en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(producto); // Retorna 201 (Created).
    }


    // Actualizar producto.
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ActualizarProductoDTO actualizarProductoDTO) {
        // Validar si existe el producto.
        if (!productoService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Obtener el producto actual y settear sus valores.
        Producto productoActual = productoService.findById(id);
        if (!actualizarProductoDTO.getNombre().isBlank()) { productoActual.setNombreProducto(actualizarProductoDTO.getNombre()); }
        if (!actualizarProductoDTO.getDescripcion().isBlank()) { productoActual.setDescripcion(actualizarProductoDTO.getDescripcion()); }
        if (actualizarProductoDTO.getPrecio() != null) { productoActual.setPrecio(actualizarProductoDTO.getPrecio()); }

        // Guardar el producto actualizado y lo devuelve.
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
        Long stockId = producto.getStock().getIdStock();

        productoService.delete(id);     // Elimina el producto
        stockService.delete(stockId);   // Elimina el stock relacionado

        return ResponseEntity.noContent().build(); // 204 OK
    }


    // Actualizar stock.
    @PutMapping("/{id}/stock")
    public ResponseEntity<Object> actualizarStockProducto(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        if (!productoService.existById(id)) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el id retorna 404 (Not Found)
        }

        // Obtendremos el producto actual según su ID.
        Producto productoActual = productoService.findById(id);
        Stock stock = productoActual.getStock();

        if (stockDTO.getNuevoStock() == null || stockDTO.getNuevoStock() < 0) {
            return ResponseEntity.badRequest().body("El stock no puede ser menor a 0");
        }

        stock.setCantidad(stockDTO.getNuevoStock());
        stockService.save(stock);

        productoActual.setStock(stock);
        productoService.save(productoActual);

        return ResponseEntity.ok(productoActual);
    }


    // Actualizar categoria de un producto.
    @PutMapping("/{id}/categoria")
    public ResponseEntity<Object> actualizarCategoriaProducto(@PathVariable Long id, @RequestBody ActualizarCategoriaDTO actualizarCategoriaDTO) {
        // Validar si existe el producto.
        if (!productoService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Validar que el nombre de categoria no este vacio.
        if (actualizarCategoriaDTO.getNombreCategoria() == null || actualizarCategoriaDTO.getNombreCategoria().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre de la categoria no puede estar vacio.");
        }

        // Instanciamos producto y categoria.
        Producto producto = productoService.findById(id);
        Optional<Categoria> categoria = categoriaService.findByNombreCategoria(actualizarCategoriaDTO.getNombreCategoria());

        // Validar si se encontro categoria.
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoria " + actualizarCategoriaDTO.getNombreCategoria() + " no encontrada.");
        }

        // Asignamos la nueva categoria.
        producto.setCategoria(categoria.get());
        productoService.save(producto);
        return ResponseEntity.ok(producto);
    }


    // Buscar productos disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> productosDisponibles() {
        List<Producto> lista = productoService.buscarProductoDisponibles();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }


    // Buscar productos no disponibles
    @GetMapping("/!disponibles")
    public ResponseEntity<List<Producto>> productosNoDisponibles() {
        List<Producto> lista = productoService.buscarProductosNoDisponibles();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }


    // Buscar y filtrar productos
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscar(@RequestBody BuscarDTO buscarDTO) {
        List<Producto> productoList = productoService.buscar(buscarDTO.getNombreProducto(), buscarDTO.getNombreCategoria());
        if (productoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productoList);
    }


}
