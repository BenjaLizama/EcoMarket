package com.ecomarket.autenticacionusuario.service;


import com.ecomarket.autenticacionusuario.model.MetodoPago;
import com.ecomarket.autenticacionusuario.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public MetodoPago save(MetodoPago metodoPago) { return metodoPagoRepository.save(metodoPago); }

    public Optional<MetodoPago> findById(Long id) { return metodoPagoRepository.findById(id); }

}
