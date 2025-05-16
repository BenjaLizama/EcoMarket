package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;


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
