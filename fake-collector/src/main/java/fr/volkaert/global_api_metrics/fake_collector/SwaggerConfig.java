package fr.volkaert.global_api_metrics.fake_collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.volkaert.global_api_metrics"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Global API Metrics Collector",
                "This is a REST API to collect Global API Metrics (from various local API metrics repositories or API gateways)",
                "v1.0",
                null,
                new Contact("Fabrice VOLKAERT", "https://github.com/volkaert", "fabrice_volkaert@yahoo.fr"),
                "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}

