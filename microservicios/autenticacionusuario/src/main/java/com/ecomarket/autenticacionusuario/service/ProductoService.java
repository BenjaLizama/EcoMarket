package com.ecomarket.autenticacionusuario.service;

import com.ecomarket.autenticacionusuario.model.ProductoMP;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ProductoMP>  obtenerProductos() {
        String URL_PRODUCTOS = "http://localhost:9093/api/v1/productos";

        ResponseEntity<List<ProductoMP>> respuesta = restTemplate.exchange(
                URL_PRODUCTOS,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductoMP>>() {}
        );

        return respuesta.getBody();
    }

}
