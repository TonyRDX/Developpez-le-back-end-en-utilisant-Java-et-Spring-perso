package com.openclassrooms.P3.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;



@Configuration
public class GlobalConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setSkipNullEnabled(true);
        return mapper;
    }

    @Bean
    public OpenAPI api() {
        String schemeName = "bearerAuth";
        return new OpenAPI()
            .info(new Info().title("Mon API").version("v1"))
            .components(new Components().addSecuritySchemes(
                schemeName,
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT") 
            ))
            .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
