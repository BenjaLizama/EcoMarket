package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "tarjeta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTarjeta")  // Evita la recursión infinita
@JsonIgnoreProperties({"metodoPagoList"})  // Ignorar la propiedad para evitar recursión
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;

    @Column(nullable = false, unique = true)
    private String numeroTarjeta;

    @Column(nullable = false)
    private String codigoTarjeta;

    @Column(nullable = false)
    private String datosDuenioTarjeta;

}
