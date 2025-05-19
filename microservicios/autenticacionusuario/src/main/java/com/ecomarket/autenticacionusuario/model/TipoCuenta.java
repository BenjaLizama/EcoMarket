package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "tipoCuenta")
    @JsonIgnore
    private List<Usuario> usuarios;

}
