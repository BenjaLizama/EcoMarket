package com.ecomarket.productoseinventario.assembler;

import com.ecomarket.productoseinventario.controller.ProductoController;
import com.ecomarket.productoseinventario.dto.ActualizarProductoDTO;
import com.ecomarket.productoseinventario.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class ProductoAssembler implements RepresentationModelAssembler<Producto,EntityModel<Producto>> {
    @Autowired
    private ActualizarProductoDTO actualizarProductoDTO;
    @Override
    public EntityModel<Producto> toModel(Producto producto){
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).obtenerProducto(producto.getIdProducto())).withRel("aobtener productos"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getIdProducto())).withRel("elimar producto"),
                linkTo(methodOn(ProductoController.class).actualizarProducto(producto.getIdProducto(),null)).withRel("actualizar productos")




    );}
}
