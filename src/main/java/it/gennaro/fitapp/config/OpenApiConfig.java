package it.gennaro.fitapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
                        .description("API demo per workout & piani fitness"))
                .addServersItem(new Server().url("/"));
    }
}
