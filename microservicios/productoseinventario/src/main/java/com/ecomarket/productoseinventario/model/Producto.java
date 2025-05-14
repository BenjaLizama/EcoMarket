package com.ecomarket.productoseinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    // Implementar conexiones (Categoria, Stock)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "stock")
    private Stock stock;

    @ManyToOne
    @JoinTable(name = "categoria")
    private Categoria categoria;

}
