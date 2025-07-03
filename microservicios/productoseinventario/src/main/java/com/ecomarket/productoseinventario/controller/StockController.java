//
package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@Tag(name = "Stock", description = "Operaciones relacionadas con el stock de productos")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Listar todos los registros de stock (uso interno/testeo)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de stocks obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Stock.class)))),
            @ApiResponse(responseCode = "204", description = "No hay stocks disponibles")
    })


    // Lista todos los stocks existentes. (Solo para testeo)
    @GetMapping
    public ResponseEntity<List<Stock>> listar() {
        List<Stock> stocks = stockService.findAll();

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(stocks);
    }

}
