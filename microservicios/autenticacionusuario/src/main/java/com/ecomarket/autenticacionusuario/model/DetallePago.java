package com.ecomarket.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePago;

    @OneToOne(mappedBy = "detallePago")
    private Usuario usuario;

}
