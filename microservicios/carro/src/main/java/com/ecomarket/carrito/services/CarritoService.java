package com.ecomarket.carrito.services;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.model.ProductoMSDTO;
import com.ecomarket.carrito.repository.CarritoRepository;
import com.ecomarket.carrito.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemRepository itemRepository;

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
        if (!carritoRepository.existsByIdUsuario(idUsuario)) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        Optional<Carrito> carrito = carritoRepository.findById(idUsuario);
        List<Item> productos = carrito.get().getItems();
        return productos;
    }

}
