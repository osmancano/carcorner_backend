package com.ironyard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class CarCornerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCornerApplication.class, args);
	}


	@Bean
	public Docket movieApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("cars-opened-api")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/open/*.*"))
				.build()
				.globalOperationParameters(
						newArrayList(new ParameterBuilder()
								.name("x-authorization-key")
								.description("API Authorization Key")
								.modelRef(new ModelRef("string"))
								.parameterType("header")
								.required(true)
								.build()));

	}

	@Bean
	public Docket CarsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Cars-secured-api")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/secure/*.*"))
				.build()
				.globalOperationParameters(
						newArrayList(new ParameterBuilder()
								.name("x-access-token")
								.description("API Authorization Key")
								.modelRef(new ModelRef("string"))
								.parameterType("header")
								.required(true)
								.build()));

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("This is our API")
				.description("Do all Movie stuff here!!!")
				.termsOfServiceUrl("http://theironyard.com")
				.contact("Osman Idris")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				.version("2.1")
				.build();
	}
}