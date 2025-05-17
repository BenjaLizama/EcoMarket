package com.ecomarket.autenticacionusuario.service;

import com.ecomarket.autenticacionusuario.model.Usuario;
import com.ecomarket.autenticacionusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Boolean existById(Long id) {return  usuarioRepository.existsById(id);}

    public List<Usuario> findAll() {return usuarioRepository.findAll();}

    public Usuario findById(Long id) {return usuarioRepository.findById(id).get();}

    public void delete(Long id) {usuarioRepository.deleteById(id);}

    public Usuario save(Usuario usuario) {return usuarioRepository.save(usuario);}

    public Usuario findByCorreo(String correo) { return usuarioRepository.findByCorreo(correo); }

    public void cambiarEstadoCuenta(Long id, boolean nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(nuevoEstado);
        usuarioRepository.save(usuario);
    }
}
