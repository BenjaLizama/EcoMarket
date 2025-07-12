package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public boolean existById(Long id) { return productoRepository.existsById(id); }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> buscarProductoDisponibles() { return productoRepository.buscarProductosDisponibles(); }

    public List<Producto> buscarProductosNoDisponibles() { return productoRepository.buscarProductosNoDisponibles(); }

    public List<Producto> buscar(String nombreProducto, String nombreCategoria) { return productoRepository.buscar(nombreProducto, nombreCategoria); }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

}
