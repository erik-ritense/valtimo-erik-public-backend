package com.ritense.valtimo.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration
class DemoConfiguration {
    @Bean
    @Order(400)
    fun demoSecurityConfig(): DemoSecurityConfig {
        return DemoSecurityConfig()
    }
}