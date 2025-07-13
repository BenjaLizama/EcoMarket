package com.ecomarket.autenticacionusuario.services;

import com.ecomarket.autenticacionusuario.model.MetodoPago;
import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.repository.UsuarioRepository;
import com.ecomarket.autenticacionusuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario crearUsuarioEjemplo() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("correoPrueba@gmail.com");
        usuario.setNombre("Usuario");
        usuario.setApellido("Prueba");
        usuario.setClave("1234");
        usuario.setDireccion("Santiago");
        usuario.setEstado(true);
        usuario.setMetodoPago(new MetodoPago());
        return usuario;
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals("Usuario", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testBuscarUsuarioPorId() {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testEliminarUsuario() {
        usuarioService.delete(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testExistePorId() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean existe = usuarioService.existById(1L);

        assertTrue(existe);
        verify(usuarioRepository).existsById(1L);
    }

    @Test
    void testExistePorCorreo() {
        when(usuarioRepository.existsByCorreo("correoPrueba@gmail.com")).thenReturn(true);

        boolean existe = usuarioService.existsByCorreo("correoPrueba@gmail.com");

        assertTrue(existe);
        verify(usuarioRepository).existsByCorreo("correoPrueba@gmail.com");
    }

    @Test
    void testBuscarPorCorreo() {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioRepository.findByCorreo("correoPrueba@gmail.com")).thenReturn(usuario);

        Usuario resultado = usuarioService.findByCorreo("correoPrueba@gmail.com");

        assertNotNull(resultado);
        assertEquals("Usuario", resultado.getNombre());
        verify(usuarioRepository).findByCorreo("correoPrueba@gmail.com");
    }

    @Test
    void testUsuariosActivos() {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioRepository.usuariosActivos()).thenReturn(List.of(usuario));

        List<Usuario> lista = usuarioService.usuariosActivos();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        verify(usuarioRepository).usuariosActivos();
    }

    @Test
    void testCambiarEstadoCuenta() {
        Usuario usuario = crearUsuarioEjemplo();
        usuario.setEstado(false);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.cambiarEstadoCuenta(1L, true);

        assertTrue(usuario.getEstado());
        verify(usuarioRepository).save(usuario);
    }
}
