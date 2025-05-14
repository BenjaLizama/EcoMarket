package com.ecomarket.productoseinventario.repository;

import com.ecomarket.productoseinventario.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Categoria, Long> {

}
