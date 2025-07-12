package com.ecomarket.productoseinventario.controller;

import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status, anyOf(is(200), is(204)));
                });
    }


    @Test
    void testBuscarProductoNoExistente() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/v1/productos/{id}", id))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                   assertThat(status, is(404));
                });
    }


}
