package com.ecomarket.autenticacionusuario.service;

import com.ecomarket.autenticacionusuario.model.TipoCuenta;
import com.ecomarket.autenticacionusuario.repository.TipoCuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TipoCuentaService {

    @Autowired
    private TipoCuentaRepository tipoCuentaRepository;

    public TipoCuenta findById(Long id) { return  tipoCuentaRepository.findById(id).get(); }

}
