package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.curso.config.property.CursoProperty;

@SpringBootApplication
@EnableConfigurationProperties(CursoProperty.class)
public class CursoApplication /*extends SpringBootServletInitializer*/ {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(CursoApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

}