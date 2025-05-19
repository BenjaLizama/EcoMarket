package com.ecomarket.autenticacionusuario.validator;

import com.ecomarket.autenticacionusuario.dto.CrearUsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class CrearUsuarioDTOValidator {

    public static List<String> validarErrores(CrearUsuarioDTO crearUsuarioDTO) {
        List<String> errores = new ArrayList<>();

        if (crearUsuarioDTO.getCorreo().isBlank() || crearUsuarioDTO.getCorreo() == null) {
            errores.add("El correo no puede ser nulo.");
        }

        if (crearUsuarioDTO.getClave().length() < 12) {
            errores.add("La clave debe contener al menos 12 caracteres");
        }

        if (crearUsuarioDTO.getNombre().isBlank() || crearUsuarioDTO.getNombre() == null) {
            errores.add("El nombre no puede ser nulo.");
        }

        if (crearUsuarioDTO.getApellido().isBlank() || crearUsuarioDTO.getApellido() == null) {
            errores.add("El apellido no puede ser nulo");
        }

        return errores;
    }

}
