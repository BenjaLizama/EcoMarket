package com.ecomarket.autenticacionusuario.service;


import com.ecomarket.autenticacionusuario.model.Tarjeta;
import com.ecomarket.autenticacionusuario.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public Tarjeta save(Tarjeta tarjeta) { return tarjetaRepository.save(tarjeta); }

}
