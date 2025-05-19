package com.ecomarket.autenticacionusuario.service;

import com.ecomarket.autenticacionusuario.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class CarritoMCService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_LISTA_PRODUCTOS = "http://localhost:9092/api/v1/carrito/productos/";
    private final String URL_CREAR = "http://localhost:9092/api/v1/carrito/crear/";

    public void crearCarrito(Long idUsuario) {
        try {
            restTemplate.postForEntity(URL_CREAR + idUsuario, null, Void.class);
        } catch (Exception e) {
            System.err.println("Error al crear el carrito: " + e.getMessage());
        }
    }


    public List<ItemDTO> listarProductosCarrito(Long idUsuario) {
        try {
            ResponseEntity<List<ItemDTO>> respuesta = restTemplate.exchange(
                    URL_LISTA_PRODUCTOS + idUsuario,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ItemDTO>>() {}
            );
            return respuesta.getBody();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

}
