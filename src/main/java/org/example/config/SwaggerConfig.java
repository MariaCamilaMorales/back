package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;


@Configuration
public class SwaggerConfig {

   /* @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .description("This is my API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")));
    }*/
}
