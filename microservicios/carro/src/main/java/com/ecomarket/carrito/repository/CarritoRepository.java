package com.ecomarket.carrito.repository;

import com.ecomarket.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    public Boolean existsByIdUsuario(Long idUsuario);

}
