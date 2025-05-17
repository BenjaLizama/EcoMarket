package com.ecomarket.autenticacionusuario.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private boolean estado; // Lombok genera getters y setters de forma automatica. Si quieres crear uno asegurate de que este contenga el nombre de la variable de la siguiente forma: (getEstado o setEstado)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_pago_id")
    @JsonManagedReference
    private DetallePago detallePago;
}
