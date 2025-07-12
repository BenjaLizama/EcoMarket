package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.services.CategoriaService;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.ecomarket.productoseinventario.services.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private StockService stockService;


    @Test
    void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status, anyOf(is(200), is(204)));
                });
    }


    @Test
    void testObtenerProductoNoExistentePorId() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/v1/productos/{id}", id))
                .andExpect(status().isNotFound());
    }


    @Transactional
    @Test
    void testObtenerProductoExistentePorId() throws Exception {
        Stock stock = new Stock();
        stock.setCantidad(0);
        stock.setFecha_actualizacion(LocalDateTime.now());
        stockService.save(stock);

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Categoria de prueba " + UUID.randomUUID());
        categoriaService.save(categoria);

        Producto producto = new Producto();
        producto.setStock(stock);
        producto.setCategoria(categoria);
        producto.setNombreProducto("Producto de prueba");
        producto.setPrecio(1500);
        producto.setDescripcion("Descripcion del producto");

        productoService.save(producto);

        Long id = producto.getIdProducto();
        mockMvc.perform(get("/api/v1/productos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Producto de prueba"))
                .andExpect(jsonPath("$.precio").value(1500));
    }


    @Test
    void testActualizarStockProductoExistente() throws Exception {
        Stock stock = new Stock();
        stock.setCantidad(0);
        stock.setFecha_actualizacion(LocalDateTime.now());
        stockService.save(stock);

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Categoria de prueba " + UUID.randomUUID());
        categoriaService.save(categoria);

        Producto producto = new Producto();
        producto.setStock(stock);
        producto.setCategoria(categoria);
        producto.setNombreProducto("Producto de prueba");
        producto.setPrecio(1500);
        producto.setDescripcion("Descripcion del producto");

        Long id = producto.getIdProducto();
        mockMvc.perform(put("/api/v1/productos/{}"));
    }
}
