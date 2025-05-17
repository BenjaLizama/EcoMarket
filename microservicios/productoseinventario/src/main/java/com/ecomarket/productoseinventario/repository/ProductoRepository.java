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

    @Query(value = "SELECT * FROM producto p LEFT OUTER JOIN stock s ON p.stock_id = s.id WHERE s.cantidad > 0;", nativeQuery = true)
    List<Producto> buscarProductosDisponibles();

}
