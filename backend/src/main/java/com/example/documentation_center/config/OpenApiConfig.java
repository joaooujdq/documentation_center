package com.example.documentation_center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API RESTful Sistemas de Recados")
                        .version("V1")
                        .description("Sistema de Recadosss")
                        .termsOfService("http://swagger.io/terms")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
