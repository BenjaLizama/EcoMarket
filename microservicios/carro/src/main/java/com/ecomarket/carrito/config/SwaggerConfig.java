package com.ecomarket.carrito.config;


    import io.swagger.v3.oas.models.OpenAPI;
    import io.swagger.v3.oas.models.info.Info;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class SwaggerConfig {


        @Bean
        public OpenAPI customOpenAPI (){
            return new OpenAPI()
                    .info(new Info()
                            .title("API 2025 Carrito")
                            .version("1.0")
                            .description("documentacion de la API para el sistema de carrito"));








        }
    }