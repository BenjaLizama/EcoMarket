package com.ecomarket.autenticacionusuario.repository;

import com.ecomarket.autenticacionusuario.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {



}
