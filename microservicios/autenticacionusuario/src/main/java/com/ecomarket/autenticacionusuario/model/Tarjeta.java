package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    private String numeroTarjeta; // Usar Integer puede ser un problema, si empieza por 0 este sera eliminado/ignorado. Aparte no vamos a hacer operaciones matematicas.

    @Column(nullable = false)
    private String codigoTarjeta;

    @Column(nullable = false)
    private String datosDuenioTarjeta;

    @ManyToMany(mappedBy = "tarjetaList") // Le dice a JPA que esta relacion ya esta definida en la entidad DetallePago en su variable "tarjetaList".
    @JsonIgnore
    private Set<DetallePago> detallePagoList; // Set evia automaticamente duplicados en memoria.

}
