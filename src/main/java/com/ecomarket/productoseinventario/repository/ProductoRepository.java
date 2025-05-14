package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
