package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.repository.CategoriaRepository;
import com.ecomarket.productoseinventario.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {


    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService; // 10.600


    @Test
    void testCrearProducto() {

        Producto producto = new Producto();
        producto.setNombreProducto("Agua Mineral");
        producto.setDescripcion("Botella 500ml");
        producto.setPrecio(1000);
        producto.setCategoria(null);

        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setIdProducto(1L);
            return p;
        });

        Producto productoGuardado = productoService.save(producto);

        assertThat(productoGuardado.getIdProducto()).isNotNull();
        assertThat(productoGuardado.getNombreProducto()).isEqualTo("Agua Mineral");

        verify(productoRepository).save(producto);

    }


    @Test
    void testBuscarProductoPorId() {

        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Agua Mineral");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreProducto()).isEqualTo("Agua Mineral");

        verify(productoRepository).findById(1L);

    }


    @Test
    void testEliminarProducto() {

        doNothing().when(productoRepository).deleteById(1L);

        productoService.delete(1L);

        verify(productoRepository).deleteById(1L);

    }

}
