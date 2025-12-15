package hu.bereczki.learn.microservices.movierating.apigateway

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
                .map { oidcUser -> exchange
                    .mutate()
                    .request { r -> r.headers { headers -> headers.setBearerAuth(oidcUser.idToken.tokenValue) } }
                    .build()
                }
                .defaultIfEmpty(exchange)
                .flatMap { exchange -> chain.filter(exchange) }
        }
    }
}