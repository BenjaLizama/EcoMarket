package com.ecomarket.autenticacionusuario;

import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.repository.UsuarioRepository;
import com.ecomarket.autenticacionusuario.service.TipoCuentaService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Random;

@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner {
        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private TipoCuentaService tipoCuentaService;
        @Override
        public void run(String... args) throws Exception {
            Faker faker = new Faker();
            Random random = new Random();

            //Generar no se lol
            for (int i = 0; i < 10; i++) {
                Usuario nuevoUsuario = new Usuario();

                nuevoUsuario.setCorreo(faker.internet().emailAddress());
                nuevoUsuario.setClave(""+faker.number().randomDigit());
                nuevoUsuario.setNombre(faker.name().firstName());
                nuevoUsuario.setApellido(faker.name().lastName());
                nuevoUsuario.setDireccion(faker.university().name());
                nuevoUsuario.setTipoCuenta(tipoCuentaService.findById(1L));
                nuevoUsuario.setEstado(true);

                usuarioRepository.save(nuevoUsuario);
            }
        }

}