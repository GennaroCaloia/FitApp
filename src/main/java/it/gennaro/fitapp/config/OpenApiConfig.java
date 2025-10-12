package it.gennaro.fitapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fitappOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fitapp API")
                        .version("v1")
                        .description("""
          Backend demo per piani/allenamenti con JWT. 
          Usa il profilo <code>nodb</code> per avvio rapido, <code>dev</code> per Postgres + Liquibase.
        """))
                .addServersItem(new Server().url("/"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
