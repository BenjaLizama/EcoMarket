package com.ecomarket.productoseinventario;


import com.ecomarket.productoseinventario.model.Producto;
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
    private CategoriaService categoriaService;
    @Override

    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Producto nuevoproducto = new Producto();

            nuevoproducto.setNombreProducto(faker.commerce().productName());
            nuevoproducto.setPrecio(faker.number().numberBetween(1000, 20000));
            nuevoproducto.setDescripcion(faker.lorem().sentence());
            productoRepository.save(nuevoproducto);


        }


    }




}
