package magicTheBuilder.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("deck-service", predicateSpec -> predicateSpec
                        .path("/api/decks")
                        .and()
                        .path("/api/collections")
                        .uri("http://localhost:8082"))
                .route("card-service", predicateSpec -> predicateSpec
                        .path("/api/cards")
                        .uri("http://localhost:8085"))
                .route("auth-service", predicateSpec -> predicateSpec
                        .path("/api/auth")
                        .uri("http://localhost:9000"))
                .build();
    }
}
