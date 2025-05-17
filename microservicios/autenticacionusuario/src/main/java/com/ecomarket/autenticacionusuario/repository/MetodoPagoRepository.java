package com.ecomarket.autenticacionusuario.repository;

import com.ecomarket.autenticacionusuario.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {



}
