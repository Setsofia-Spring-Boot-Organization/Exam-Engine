package com.examengine.exam_engine.configurations

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.beans.factory.annotation.Value


@Configuration
@RequiredArgsConstructor
class CorsConfig(
   @Value("\${frontend-url}") private val frontendURL: String,
    @Value("\${deployed-frontend-url}") private val deployedFrontendURL: String
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/exam-engine/api/v1/auth/**")
            .allowedOrigins("$frontendURL/", "$deployedFrontendURL/")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH")
        super.addCorsMappings(registry)
    }
}