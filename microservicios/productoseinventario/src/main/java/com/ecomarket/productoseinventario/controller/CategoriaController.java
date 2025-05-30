package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {


    @Autowired
    private CategoriaService categoriaService;


    // Obtener todas las categorias existentes.
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        if (categorias.isEmpty()) { // Si no se encuentran elementos dentro de la lista categorias.
            return ResponseEntity.noContent().build(); // Retorna 204 (No Content).
        }
        return ResponseEntity.ok(categorias); // Si encuentra elementos retorna 200 (OK).
    }


    // Agregar categoria.
    @PostMapping("/agregar")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaNueva = new Categoria(); // Se instancia una nueva categoria.
        categoriaNueva.setNombreCategoria(categoria.getNombreCategoria()); // Se settea el nombre de la categoria a categoriaNueva.

        categoriaService.save(categoriaNueva); // Se almacena la nueva categoria en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNueva); // Se retorna la respuesta http 200 (Ok).
    }


    // Modificar nombre categoria.
    @PutMapping("/modificar/{id}/{nombre}")
    public ResponseEntity<Categoria> modificarNombreCategoria(@PathVariable Long id, @PathVariable String nombre) {
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build(); // Si no existe retorna 404 (Not Found)
        }

        Categoria categoria = categoriaService.findById(id).get(); // Se obtiene la categoria por su id.
        categoria.setNombreCategoria(nombre); // Se settea el nuevo nombre.

        categoriaService.save(categoria);
        return ResponseEntity.ok(categoria); // Retorna 200 (OK) y muestra la categoria.
    }


    // Eliminar categoria.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Si no existe la categoria retorna 404 (Not Found)
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Se elimina la categoria de la base de datos.
        categoriaService.delete(id);
        // Se retorna la respuesta del servidor 204 (No Content)
        return ResponseEntity.noContent().build();
    }


    // Obtener listado de productos de categoria.
    @GetMapping("/productos/{id}")
    public ResponseEntity<List<Producto>> obtenerListaProductos(@PathVariable Long id) {
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<Producto> listaProductos = categoriaService.findById(id).get().getProductoList();
        return ResponseEntity.ok(listaProductos);
    }
}
