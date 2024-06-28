package br.com.mateus.taskorganizer.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SpringDocConfigurations {
	
	@Bean
	 OpenAPI customOpenAPI() {
	   return new OpenAPI()
			.components(new Components()
			.addSecuritySchemes("bearer-key",
			new io.swagger.v3.oas.models.security.SecurityScheme()
			.type(Type.HTTP).scheme("bearer")
			.bearerFormat("JWT")))
			.info(new Info()
				.title("Task.Organizer API")
					.description("Rest API of the Task.Organizer application, containing task CRUD functionalities")
					.contact(new Contact()
						.name("Mateus Pontes")
						.email("mateuspsdd@gmail.com")));
	}
}