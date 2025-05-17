package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

   /*
   Se generan metodos por defecto:
   - findAll()
   - findById()
   - save()
   - deleteById()
   - existsById()
   - count()
    */

    // Se encarga de traer los productos que tienen stock disponible.
    @Query("SELECT p FROM Producto p WHERE p.stock.cantidad > 0")
    List<Producto> buscarProductosDisponibles();

    // Se encarga de traer los productos que no tienen stock disponible.
    @Query("SELECT p FROM Producto p WHERE p.stock.cantidad <= 0")
    List<Producto> buscarProductosNoDisponibles();

}
