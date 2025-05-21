package com.ecomarket.carrito.model;

public class ProductoMSDTO {

    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Integer precio;

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }
}
