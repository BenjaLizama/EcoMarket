package com.ecomarket.autenticacionusuario.repository;

import com.ecomarket.autenticacionusuario.model.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePago, Long> {



}
