package com.ecomarket.productoseinventario.model;

import com.ecomarket.productoseinventario.repository.StockRepository;
import com.ecomarket.productoseinventario.services.CategoriaService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    // Implementar conexiones (Categoria, Stock)

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stock_id")
    @JsonManagedReference
    private Stock stock;


    @ManyToOne
    @JoinColumn(name = "categoria")
    @JsonManagedReference
    private Categoria categoria;

}
