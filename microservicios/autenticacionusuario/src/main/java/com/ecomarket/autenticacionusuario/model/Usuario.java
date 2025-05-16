package com.ecomarket.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {


    @Column(nullable = false,unique = true)
    private String correo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String clave;

    @Column(nullable = true)
    private String direccion;

    @Column(nullable = false)
    private int tipoUsuario;

    @Column(nullable = false)
    private boolean estado;

    public void setActivo(boolean estado) {
        this.estado = estado;
    }

}
