package com.minhdunk.research.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;

    @Value("${bezkoder.openapi.prod-url}")
    private String prodUrl;

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("nguyendangminh49@gmail.com");
        contact.setName("Nguyen Dang Minh");
        contact.setUrl("https://www.facebook.com/minhdunk/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Teaching Method Research API by MinhDunk")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage demo.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer))
                .addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }
}