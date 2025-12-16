package hu.bereczki.learn.microservices.movierating.commentservice

import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.AbstractOAuth2Token
import org.springframework.web.client.RestTemplate


@Configuration
class WebClientConfig {

    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate =
        restTemplateBuilder
            .interceptors(bearerTokenExchangeInterceptor())
            .build()

    fun bearerTokenExchangeInterceptor(): ClientHttpRequestInterceptor {
        return ClientHttpRequestInterceptor { request, body, execution ->
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
                ?: return@ClientHttpRequestInterceptor execution.execute(request, body)

            if (authentication.credentials !is AbstractOAuth2Token) {
                return@ClientHttpRequestInterceptor execution.execute(request, body)
            }

            val token = authentication.credentials as AbstractOAuth2Token
            request.headers.setBearerAuth(token.tokenValue)
            execution.execute(request, body)
        }
    }
}