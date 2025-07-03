//
package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "Operaciones relacionadas con categorías y sus productos")
public class CategoriaController {


    @Autowired
    private CategoriaService categoriaService;
    @Operation(summary = "Obtener todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Categoria.class)))),
            @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    })


    // Obtener todas las categorias existentes.
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        if (categorias.isEmpty()) { // Si no se encuentran elementos dentro de la lista categorias.
            return ResponseEntity.noContent().build(); // Retorna 204 (No Content).
        }
        return ResponseEntity.ok(categorias); // Si encuentra elementos retorna 200 (OK).
    }

    @Operation(summary = "Agregar una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Categoria.class)))
    })


    // Agregar categoria.
    @PostMapping("/agregar")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria)
    {
        Categoria categoriaNueva = new Categoria(); // Se instancia una nueva categoria.
        categoriaNueva.setNombreCategoria(categoria.getNombreCategoria()); // Se settea el nombre de la categoria a categoriaNueva.

        categoriaService.save(categoriaNueva); // Se almacena la nueva categoria en la base de datos.
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNueva); // Se retorna la respuesta http 200 (Ok).
    }

    @Operation(summary = "Modificar el nombre de una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre de la categoría modificado",
                    content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })



    // Modificar nombre categoria.
    @PutMapping("/modificar/{id}/{nombre}")

    public ResponseEntity<Categoria> modificarNombreCategoria(@Parameter(description = "ID de la categoría", example = "1")@PathVariable Long id,  @Parameter(description = "Nuevo nombre de la categoría", example = "Lácteos")@PathVariable String nombre) {
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build(); // Si no existe retorna 404 (Not Found)
        }

        Categoria categoria = categoriaService.findById(id).get(); // Se obtiene la categoria por su id.
        categoria.setNombreCategoria(nombre); // Se settea el nuevo nombre.

        categoriaService.save(categoria);
        return ResponseEntity.ok(categoria); // Retorna 200 (OK) y muestra la categoria.
    }

    @Operation(summary = "Eliminar una categoría por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })


    // Eliminar categoria.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la categoría a eliminar", example = "1") @PathVariable Long id) {
        // Si no existe la categoria retorna 404 (Not Found)
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Se elimina la categoria de la base de datos.
        categoriaService.delete(id);
        // Se retorna la respuesta del servidor 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener productos de una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })


    // Obtener listado de productos de categoria.
    @GetMapping("/productos/{id}")
    public ResponseEntity<List<Producto>> obtenerListaProductos( @Parameter(description = "ID de la categoría", example = "1")@PathVariable Long id) {
        if (!categoriaService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<Producto> listaProductos = categoriaService.findById(id).get().getProductoList();
        return ResponseEntity.ok(listaProductos);
    }
}
