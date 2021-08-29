package br.com.southsystem.cooperativa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.cooperativa.controller.v1"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "API Cooperativa",
                "API para gerenciamento de sessão de uma cooperativa",
                "1.0",
                "Termos e serviços",
                new Contact("Luancarlos Rocha", "", "luanbam@hotmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licence.html", new ArrayList<>()
        );
    }


}