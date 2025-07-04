package com.ecomarket.productoseinventario;


import com.ecomarket.productoseinventario.model.Categoria;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.model.Stock;
import com.ecomarket.productoseinventario.repository.CategoriaRepository;
import com.ecomarket.productoseinventario.repository.ProductoRepository;
import com.ecomarket.productoseinventario.services.CategoriaService;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.ecomarket.productoseinventario.services.StockService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;


import java.time.LocalDateTime;
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
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductoService productoService;
    @Override

    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Stock nuevoStock = new Stock();
            nuevoStock.setCantidad(faker.number().numberBetween(10, 100));
            nuevoStock.setFecha_actualizacion(LocalDateTime.now());
            stockService.save(nuevoStock);

            Producto nuevoproducto = new Producto();
            nuevoproducto.setNombreProducto(faker.commerce().productName());
            nuevoproducto.setPrecio(faker.number().numberBetween(1000, 20000));
            nuevoproducto.setDescripcion(faker.lorem().sentence());


            productoService.save(nuevoproducto);

            nuevoproducto.setStock(nuevoStock);

            nuevoproducto.setCategoria(categoriaService.findById(faker.number().numberBetween(20L, 22L)).orElse(null));
            productoService.save(nuevoproducto);



        }
    }




}
