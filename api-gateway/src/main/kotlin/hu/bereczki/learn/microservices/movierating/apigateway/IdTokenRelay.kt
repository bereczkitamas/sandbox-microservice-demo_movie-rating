package hu.bereczki.learn.microservices.movierating.apigateway

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import java.security.Principal

/**
 * Token Relay for OAuth2 token when ID token should be used. E.g., Google OAuth provides an opaque token as
 * Access Token, which cannot be used for downstream services to validate.
 * However, if you are using Okta or Keycloak, they provide JWT-based Access Token, and default Token Relay will work.
 */
class IdTokenRelay : AbstractGatewayFilterFactory<Any>() {
    override fun apply(config: Any): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            exchange.getPrincipal<Principal>()
                .filter { principal -> principal is OAuth2AuthenticationToken }
                .cast(OAuth2AuthenticationToken::class.java)
                .filter { authToken: OAuth2AuthenticationToken -> authToken.principal is OidcUser }
                .map { it.principal as OidcUser }
                .map { it.idToken.tokenValue }
                .doOnNext { logger.debug("Relaying ID token: {}", it) }
                .map { tokenValue -> exchange
                    .mutate()
                    .request { r -> r.headers { headers -> headers.setBearerAuth(tokenValue) } }
                    .build()
                }
                .defaultIfEmpty(exchange)
                .flatMap { exchange -> chain.filter(exchange) }
        }
    }
}

val logger: Logger = LoggerFactory.getLogger(IdTokenRelay::class.java)