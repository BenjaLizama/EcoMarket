package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = "SELECT * FROM producto WHERE id = :id", nativeQuery = true)
    Producto buscarPorId(@Param("id")Integer id);

    @Query(value = "SELECT * FROM producto WHERE categoria = :categoria", nativeQuery = true)
    Producto buscarPorCategoria(@Param("categoria")String categoria);

}
