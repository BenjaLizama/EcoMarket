package com.ecomarket.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoCuenta;

    @Column(nullable = false)
    private String nombreTipoCuenta;

}
