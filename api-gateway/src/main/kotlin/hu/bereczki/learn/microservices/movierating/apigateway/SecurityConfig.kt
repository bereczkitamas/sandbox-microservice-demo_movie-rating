package hu.bereczki.learn.microservices.movierating.apigateway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .authorizeExchange { it.anyExchange().authenticated() }
            .oauth2Login ( Customizer.withDefaults() )
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .logout { it.logoutUrl("/logout") }
            .build()
}