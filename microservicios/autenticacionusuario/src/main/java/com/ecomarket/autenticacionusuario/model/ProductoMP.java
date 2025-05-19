package com.ecomarket.autenticacionusuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoMP {

    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Integer precio;

}
