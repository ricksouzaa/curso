package com.curso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
//				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,
//						Arrays.asList(
//								new ResponseMessageBuilder()
//								.code(500)
//								.message("Erro interno no servidor")
//								.responseModel(new ModelRef("Error"))
//								.build(),
//								new ResponseMessageBuilder()
//								.code(400)
//								.message("Formato da mensagem inválida")
//								.responseModel(new ModelRef("Error"))
//								.build()
//								)
//						)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}