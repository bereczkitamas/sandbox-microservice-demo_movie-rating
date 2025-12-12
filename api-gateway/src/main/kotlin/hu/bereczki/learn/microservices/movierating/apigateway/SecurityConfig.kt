//package hu.bereczki.learn.microservices.movierating.apigateway
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.web.server.SecurityWebFilterChain
//
//@Configuration
//@EnableWebFluxSecurity
//class SecurityConfig
//
//@Bean
//fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
//    http
//        .authorizeExchange { it
////            .pathMatchers("/api/**")
//            .anyExchange().authenticated() }
//        .oauth2Client ( Customizer.withDefaults() )
//        .logout { it.logoutUrl("/logout") }
//        .build()