package com.ecomarket.carrito.services;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_USUARIOS = "http://localhost:9091/api/v1/usuarios/";

    public Boolean usuarioExiste(Long idUsuario) {
        try {
            ResponseEntity<String> respuesta = restTemplate.getForEntity(URL_USUARIOS + idUsuario, String.class);
            return respuesta.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    public Carrito crearCarrito(Long idUsuario) {
        if (!usuarioExiste(idUsuario)) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setIdUsuario(idUsuario);
        nuevoCarrito.setItems(new ArrayList<>());

        return carritoRepository.save(nuevoCarrito);
    }

    public List<Item> itemsCarrito(Long idUsuario) {
        try {
            ResponseEntity<List<Item>> respuesta = restTemplate.exchange(
                    URL_USUARIOS + idUsuario,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Item>>() {}
            );
            return respuesta.getBody();
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Error: " + e);
            return Collections.emptyList();
        }
    }

}
