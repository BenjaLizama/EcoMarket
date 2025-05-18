package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(value = "SELECT p FROM Producto p WHERE p.stock.cantidad > 0", nativeQuery = true)
    List<Producto> buscarProductosDisponibles();

    // Se encarga de traer los productos que no tienen stock disponible.
    @Query(value = "SELECT p FROM Producto p WHERE p.stock.cantidad <= 0", nativeQuery = true)
    List<Producto> buscarProductosNoDisponibles();

    // Buscar
    @Query(value = "SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tproducto p\n" +
            "LEFT OUTER JOIN\n" +
            "\tcategoria c ON p.categoria = c.id_categoria\n" +
            "WHERE\n" +
            "\tp.nombre_producto LIKE :nombreProducto AND\n" +
            "\tc.nombre_categoria LIKE :nombreCategoria OR c.nombre_categoria IS NULL;", nativeQuery = true)
    List<Producto> buscar(@Param("nombreProducto") String nombreProducto, @Param("nombreCategoria") String nombreCategoria);
}
