package com.chandrakumar.ms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonAPIApplication.class, args);
    }

  /*  @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.chandrakumar.ms.api.person.resource")
                .group("person-api-public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public OpenAPI personOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Person API")
                        .description("Person sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Person Wiki Documentation")
                        .url("https://Person.wiki.github.org/docs"));
    }*/

}
