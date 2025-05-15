package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll() { return stockRepository.findAll(); }

    public Stock getReferenceById(Long id) { return stockRepository.getReferenceById(id); } // "Optional<Stock>" te da más control si el objeto no existe. }

    public Stock save(Stock stock) { return stockRepository.save(stock); }

    public void delete(Long id) { stockRepository.deleteById(id); }
    
}
