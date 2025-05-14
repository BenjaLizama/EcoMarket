package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll() { return stockRepository.findAll(); }

    public Optional<Stock> findById(Long id) { return stockRepository.findById(id); } // "Optional<Stock>" te da más control si el objeto no existe.

    public void delete(Long id) { stockRepository.deleteById(id); }
    
}
