package com.ecomarket.autenticacionusuario.dto;

import lombok.*;

@Data
@Getter
@Setter
public class CrearUsuarioDTO {

    private String correo;
    private String clave;
    private String nombre;
    private String apellido;
    private String direccion;

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }


}
