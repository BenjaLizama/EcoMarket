package com.ecomarket.carrito.services;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Transactional
public class CarritoServices {

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

}
