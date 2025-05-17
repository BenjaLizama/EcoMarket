package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")  // Evita la recursión infinita
public class Usuario {

    @Column(nullable = false, unique = true)
    private String correo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago; // Ya no es necesario JsonManagedReference
}
