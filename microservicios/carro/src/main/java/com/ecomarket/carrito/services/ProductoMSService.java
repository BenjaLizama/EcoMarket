package com.ecomarket.carrito.services;

import com.ecomarket.carrito.model.Carrito;
import com.ecomarket.carrito.model.Item;
import com.ecomarket.carrito.model.ProductoMSDTO;
import com.ecomarket.carrito.repository.CarritoRepository;
import com.ecomarket.carrito.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoMSService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemRepository itemRepository;

    RestTemplate restTemplate = new RestTemplate();
    private final String URL_PRODUCTOS = "http://localhost:9093/api/v1/productos";
    private final String URL_PRODUCTO = "http://localhost:9093/api/v1/productos/";


    public List<ProductoMSDTO> obtenerProductos() {
        try {
            ResponseEntity<List<ProductoMSDTO>> respuesta = restTemplate.exchange(
                    URL_PRODUCTOS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductoMSDTO>>() {}
            );
            return respuesta.getBody();
        } catch (Exception e) {
            System.err.println("No hay productos disponibles en este momento");
            return Collections.emptyList();
        }
    }

    public Carrito agregarProducto(Long idUsuario, Long idProducto) {
        if (!carritoRepository.existsByIdUsuario(idUsuario)) {
            return null;
        }

        try {
            ResponseEntity<ProductoMSDTO> respuesta = restTemplate.exchange(
                    URL_PRODUCTO + idProducto,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ProductoMSDTO>() {}
            );
            Long idProductoAgregado = respuesta.getBody().getIdProducto();

            Carrito carrito = carritoRepository.findById(idUsuario).get();

            Item itemNuevo = new Item();
            itemNuevo.setIdProducto(idProducto);
            itemNuevo.setCantidad(0);

            itemRepository.save(itemNuevo);

            carrito.getItems().add(itemNuevo);
            carritoRepository.save(carrito);

            return carrito;
        } catch (Exception e) {
            System.err.println("Error: " + e);
            return null;
        }
    }

}
