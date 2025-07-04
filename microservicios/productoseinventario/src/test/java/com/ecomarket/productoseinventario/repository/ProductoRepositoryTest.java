package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    @Test
    void testGuardarProductoYCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Bebidas");
        categoriaRepository.save(categoria);

        assertThat(categoria.getIdCategoria()).isNotNull();

        Producto producto = new Producto();
        producto.setNombreProducto("Coca-Cola 2L");
        producto.setDescripcion("Ta wena la coquitaaayuda.");
        producto.setPrecio(1700);
        producto.setCategoria(categoria);
        productoRepository.save(producto);

        assertThat(producto.getIdProducto()).isNotNull();
        assertThat(producto.getCategoria()).isEqualTo(categoria);
    }

}
