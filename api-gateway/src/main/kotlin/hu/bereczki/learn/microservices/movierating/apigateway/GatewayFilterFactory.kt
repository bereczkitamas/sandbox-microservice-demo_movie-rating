//package hu.bereczki.learn.microservices.movierating.apigateway
//
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
//import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory
//import org.springframework.cloud.gateway.filter.GatewayFilter
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
//import org.springframework.stereotype.Component
//import org.springframework.web.reactive.function.client.WebClient
//
//@Component
//class GatewayFilterFactory(webClientBuilder: WebClient.Builder, cbFactory: ReactiveCircuitBreakerFactory<?, ?>) : AbstractGatewayFilterFactory<Object>() {
//    override fun apply(config: Object): GatewayFilter {
//        TODO("Not yet implemented")
//    }
//}