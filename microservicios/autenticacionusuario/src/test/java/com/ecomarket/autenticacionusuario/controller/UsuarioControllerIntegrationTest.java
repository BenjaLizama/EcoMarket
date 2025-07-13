package com.ecomarket.autenticacionusuario.controller;

import com.ecomarket.autenticacionusuario.model.TipoCuenta;
import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.repository.TipoCuentaRepository;
import com.ecomarket.autenticacionusuario.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TipoCuentaRepository tipoCuentaRepository;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();
        tipoCuentaRepository.deleteAll();

        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setNombreTipoCuenta("Cuenta Rut");
        tipoCuenta = tipoCuentaRepository.save(tipoCuenta);

        usuario = new Usuario();
        usuario.setCorreo("integracion@test.com");
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setClave("1234");
        usuario.setDireccion("Dirección Test");
        usuario.setEstado(true);
        usuario.setTipoCuenta(tipoCuenta);

        usuario = usuarioRepository.save(usuario);
    }


    @Test
    void testBuscarUsuarioExistente() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo", is(usuario.getCorreo())));

    }

    @Test
    void testBuscarUsuarioNoExistente() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListarUsuarios() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList", hasSize(greaterThan(0))));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/" + usuario.getId()))
                .andExpect(status().isNoContent());
    }
}
