package fr.volkaert.global_api_metrics.spring_gtw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringGtwApplication {

	public static final String DEFAULT_REFERENTIAL_CODE = "GROUP";
	public static final String EXCHANGE_ATTRIBUTE_FOR_EXECUTION_START_TIME = "EXECUTION_START_TIME";

	public static void main(String[] args) {
		SpringApplication.run(SpringGtwApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplateForCollector() {
		return new RestTemplate();
	}
}
