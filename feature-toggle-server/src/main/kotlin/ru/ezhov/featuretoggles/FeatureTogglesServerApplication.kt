package ru.ezhov.featuretoggles

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FeatureTogglesServerApplication {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .info(Info().title("Feature Toggles API").version("1")
                        .license(License().name("Apache 2.0").url("http://springdoc.org")))
    }
}

fun main(args: Array<String>) {
    runApplication<FeatureTogglesServerApplication>(*args)
}
