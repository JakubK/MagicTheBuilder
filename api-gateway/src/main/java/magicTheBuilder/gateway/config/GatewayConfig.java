package magicTheBuilder.gateway.config;

import magicTheBuilder.gateway.filter.AuthorizationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthorizationFilter filter;

    public GatewayConfig(AuthorizationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("deck-service", predicateSpec -> predicateSpec
                        .path("/api/decks/**", "/api/auth/decks/**", "/api/collections/**", "/api/auth/collections/**", "api/auth/decks/myDecks")
                        .filters(f -> f.filter(filter))
                        .uri("http://deck-service:8080"))
                .route("card-service", predicateSpec -> predicateSpec
                        .path("/api/cards/**", "/api/sets", "/api/types", "/api/formats")
                        .uri("http://card-service:8080"))
                .route("user-service", predicateSpec -> predicateSpec
                        .path("/api/user/**")
                        .uri("http://user-service:8080"))
                .build();
    }
}
