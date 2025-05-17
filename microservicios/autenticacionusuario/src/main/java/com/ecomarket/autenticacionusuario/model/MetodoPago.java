package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDetallePago")  // Evita la recursión infinita
@JsonIgnoreProperties({"usuario"})  // Ignorar la propiedad para evitar recursión
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePago;

    @OneToOne(mappedBy = "metodoPago")
    private Usuario usuario;

    @ManyToMany // Relacion muchos a muchos
    @JoinTable(
            name = "tarjeta_metodo_pago",
            joinColumns = @JoinColumn(name = "metodo_pago_id"),
            inverseJoinColumns = @JoinColumn(name = "tarjeta_id")
    )
    private Set<Tarjeta> tarjetaList;
}
