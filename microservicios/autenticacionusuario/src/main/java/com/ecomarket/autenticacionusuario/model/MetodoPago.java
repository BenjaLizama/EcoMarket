package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "detalle_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePago;


    @OneToOne(mappedBy = "metodoPago")
    @JsonBackReference
    private Usuario usuario;


    @ManyToMany // Relacion muchos a muchos.
    @JoinTable( // Tabla intermedia que usara JPA para representar la relacion.
            name = "tarjeta_metodo_pago", // Nombre de la tabla intermedia.
            joinColumns = @JoinColumn(name = "metodo_pago_id"), // Define la columna en la tabla intermedia que referencia fk de detalle_pago
            inverseJoinColumns = @JoinColumn(name = "tarjeta_id") // Define la columna en la tabla intermedia que referencia fk de tarjeta
    )
    private Set<Tarjeta> tarjetaList; // Generamos una lista que contenga diferentes tarjetas.

}
