package com.example.backend.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI bipOpenApi() {
    return new OpenAPI()
            .info(new Info()
                    .title("BIP Backend API")
                    .description("APIs REST mais integração ao módulo EJB")
                    .version("1.0.0")
                    .contact(new Contact()
                            .name("André Siqueira")
                            .email("andre3s.sqr@gmail.com")));
  }
}
