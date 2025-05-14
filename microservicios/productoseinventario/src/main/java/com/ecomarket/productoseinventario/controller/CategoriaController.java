package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    // Obtener todas las categorias existentes
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        if (categorias.isEmpty()) { // Si no se encuentran elementos dentro de la lista categorias.
            return ResponseEntity.noContent().build(); // Retorna 204 (No Content).
        }
        return ResponseEntity.ok(categorias); // Si encuentra elementos retorna 200 (OK).
    }

}
