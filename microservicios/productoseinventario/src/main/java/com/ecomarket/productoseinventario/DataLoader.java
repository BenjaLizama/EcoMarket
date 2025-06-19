package com.ecomarket.productoseinventario;


import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.repository.CategoriaRepository;
import com.ecomarket.productoseinventario.repository.ProductoRepository;
import com.ecomarket.productoseinventario.services.CategoriaService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.util.Random;

@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaService categoriaService;
    @Override

    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Categoria nuevacategoria = new Categoria();
            nuevacategoria.setNombreCategoria(faker.lorem().toString());
            categoriaRepository.save(nuevacategoria);


        }

        for (int i = 0; i < 10; i++) {
            Producto nuevoproducto = new Producto();
            // IMPLEMENTARR COMO AGREGARLE LA CATEGORIA A EL PRODUCTO
            //Long idrandom = faker.number().numberBetween(1L,5L);
            //nuevoproducto.setCategoria(categoriaService.findById(idrandom));
            nuevoproducto.setNombreProducto(faker.commerce().productName());
            nuevoproducto.setPrecio(faker.number().numberBetween(1000, 20000));
            nuevoproducto.setDescripcion(faker.lorem().sentence());


            productoRepository.save(nuevoproducto);



        }


    }




}
