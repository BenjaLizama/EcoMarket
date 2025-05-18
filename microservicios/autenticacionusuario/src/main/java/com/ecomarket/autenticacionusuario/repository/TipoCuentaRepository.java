package com.ecomarket.autenticacionusuario.repository;

import com.ecomarket.autenticacionusuario.model.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCuentaRepository extends JpaRepository<TipoCuenta, Long> {



}
