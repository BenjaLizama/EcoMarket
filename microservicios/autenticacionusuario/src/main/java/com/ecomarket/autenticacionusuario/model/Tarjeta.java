package com.ecomarket.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarjeta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;

    @Column(nullable = false, unique = true)
    private Integer numeroTarjeta;

    @Column(nullable = false)
    private Integer codigoTarjeta;

    @Column(nullable = false)
    private String datosDuenioTarjeta;

}
