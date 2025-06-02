package com.customer.rewards.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Configures and returns the OpenAPI bean for the Rewards API.
	 *
	 * @return configured OpenAPI instance
	 */
	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI()
				.info(new Info()
						.title("Customer Rewards API")
						.description("REST API to calculate reward points for customers based on transactions.")
						.version("1.0.0"));
	}
}
