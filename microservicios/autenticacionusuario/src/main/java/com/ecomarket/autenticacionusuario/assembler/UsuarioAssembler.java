package com.ecomarket.autenticacionusuario.assembler;

import com.ecomarket.autenticacionusuario.controller.UsuarioController;
import com.ecomarket.autenticacionusuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).buscarUsuario(usuario.getId())).withSelfRel(),

                linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("delete"),

                linkTo(methodOn(UsuarioController.class).actualizarUsuario(usuario.getId(), null)).withRel("update"),

                linkTo(methodOn(UsuarioController.class).activacion(usuario.getId(), null)).withRel("activate"),

                linkTo(methodOn(UsuarioController.class).otenerMetodosPago(usuario.getId())).withRel("metodos_pago"),

                linkTo(methodOn(UsuarioController.class).agregarTarjeta(usuario.getId(), null)).withRel("agregar_tarjeta"),

                linkTo(methodOn(UsuarioController.class).listarProductosCarrito(usuario.getId())).withRel("carrito")
        );

    }
}

