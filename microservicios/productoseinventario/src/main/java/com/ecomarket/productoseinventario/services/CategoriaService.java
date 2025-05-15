package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() { return categoriaRepository.findAll(); }

    public Categoria save(Categoria categoria) { return categoriaRepository.save(categoria); }

    public void delete(Long id) { categoriaRepository.deleteById(id); }

}
