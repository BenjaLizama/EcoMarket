package com.ecomarket.autenticacionusuario.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarritoMCService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_CARRITO = "http://localhost:9092/api/v1/carrito/crear/";

    public void crearCarrito(Long idUsuario) {
        try {
            restTemplate.postForEntity(URL_CARRITO + idUsuario, null, Void.class);
        } catch (Exception e) {
            System.err.println("Error al crear el carrito: " + e.getMessage());
        }
    }

}
