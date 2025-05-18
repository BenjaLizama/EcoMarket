package com.ecomarket.productoseinventario.services;

import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() { return categoriaRepository.findAll(); }

    public Optional<Categoria> findById(Long id) { return categoriaRepository.findById(id); }

    public Optional<Categoria> findByNombreCategoria(String nombreCategoria) { return categoriaRepository.findByNombreCategoria(nombreCategoria); }

    public Boolean existById(Long id) { return categoriaRepository.existsById(id); }

    public Categoria save(Categoria categoria) { return categoriaRepository.save(categoria); }

    public void delete(Long id) { categoriaRepository.deleteById(id); }

}
