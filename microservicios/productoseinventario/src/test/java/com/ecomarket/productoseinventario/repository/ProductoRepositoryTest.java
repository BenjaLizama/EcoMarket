package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class ProductoRepositoryTest {


    @Autowired
    private ProductoRepository productoRepository;


    @Test
    void testGuardarProducto() {

        // Creamos el nuevo producto
        Producto producto = new Producto();
        producto.setNombreProducto("Coca-Cola 2L");
        producto.setDescripcion("Ta wena la coquitaaayuda.");
        producto.setPrecio(1700);
        producto.setCategoria(null);

        // Guardamos el nuevo producto
        productoRepository.save(producto);

        // Verificamos que el producto existe
        assertThat(producto.getIdProducto()).isNotNull();

    }


    @Test
    void testEliminarProducto() {

        // Creacion del producto a eliminar.
        Producto producto = new Producto();
        producto.setNombreProducto("Papas fritas");
        producto.setDescripcion("Son papas nomas");
        producto.setPrecio(3000);
        producto.setCategoria(null);
        productoRepository.save(producto);

        // Eliminamos el producto
        productoRepository.delete(producto);

        // Verificamos que el producto ya no existe.
        Optional<Producto> productoEliminado = productoRepository.findById(producto.getIdProducto());
        assertThat(productoEliminado).isEmpty();

    }

}
