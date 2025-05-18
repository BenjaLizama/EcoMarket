package com.ecomarket.productoseinventario.dto;

public class BuscarDTO {

    private String nombreProducto;
    private String nombreCategoria;

    public String getNombreProducto() {
        return "%"+nombreProducto+"%";
    }

    public String getNombreCategoria() {
        return "%"+nombreCategoria+"%";
    }
}
