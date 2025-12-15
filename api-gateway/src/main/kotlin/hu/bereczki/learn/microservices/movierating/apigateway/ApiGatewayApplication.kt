package hu.bereczki.learn.microservices.movierating.apigateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders

@SpringBootApplication
class ApiGatewayApplication {

    @Value($$"${movie-service.uri:http://localhost:8081}")
    lateinit var movieServiceUri: String

    @Value($$"${rating-service.uri:http://localhost:8082}")
    lateinit var ratingServiceUri: String

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("movie-service") { spec ->
                spec
                    .path("/api/movies", "/api/movies/**")
                    .filters { filterSpec ->
                        filterSpec.circuitBreaker { config -> config.setName("movie-cb") }
                        filterSpec.rewritePath("/api/movies", "/movies")
                        filterSpec.rewritePath("/api/movies/(?<segment>.*)", $$"/movies/${segment}")
//                        filterSpec.tokenRelay() // For JWT based access token forward
                        filterSpec.filter(IdTokenRelay().apply( {}))
                        filterSpec.removeRequestHeader(HttpHeaders.COOKIE)
                    }
                    .uri(movieServiceUri)
            }
            .route("rating-service") { spec ->
                spec
                    .path("/api/ratings/**")
                    .filters { filterSpec ->
                        filterSpec.circuitBreaker { config -> config.setName("rating-cb") }
                        filterSpec.rewritePath("/api/ratings/(?<segment>.*)", $$"/ratings/${segment}")
                    }
                    .uri(ratingServiceUri)
            }
            .build()
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}