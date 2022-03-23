package com.acti.swagger;

import java.util.Collections;

import org.springframework.context.annotation.*;

import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.acti.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Actividad 3-Documentacion",
				"Actividad 3-Documentacion",
				"1.0",
				"",
				new Contact("Victor", "https://localhost", "victor.martinez.nava@banregio.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}
