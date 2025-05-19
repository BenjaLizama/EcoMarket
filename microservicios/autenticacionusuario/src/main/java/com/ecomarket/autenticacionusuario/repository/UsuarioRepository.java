package com.ecomarket.autenticacionusuario.repository;


import com.ecomarket.autenticacionusuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
           /*
   Se generan metodos por defecto:
   - findAll()
   - findById()
   - save()
   - deleteById()
   - existsById()
   - count()
    */

    public Boolean existsByCorreo(String correo);

    // Obtener correo
    @Query(value = "SELECT * FROM usuario u WHERE u.correo = :correo;", nativeQuery = true)
    public Usuario findByCorreo(@Param("correo") String correo);

    // Usuarios activos
    @Query(value = "SELECT * FROM usuario u WHERE u.estado = true;", nativeQuery = true)
    public List<Usuario> usuariosActivos();

}
