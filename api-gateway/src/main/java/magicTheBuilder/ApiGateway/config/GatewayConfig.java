package magicTheBuilder.ApiGateway.config;

import magicTheBuilder.ApiGateway.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {


    final String uuidRegex = "{deckID:^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$}";
    final String userIdRegex = "{userID:\\d{7}}";
    @Autowired
    private AuthorizationFilter filter;

    // TODO: Change hardcoded links to env variables
    // TODO: Block cards/ids and cards/all
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("publicDeckServiceRoute", predicateSpec -> predicateSpec
                        .path("/api/decks/**", "/api/auth/decks/**", "/api/collections/**", "/api/auth/collections/**", "api/auth/decks/myDecks")
                        .filters(f -> f.filter(filter))
                        .uri("http://deck-service:8080"))
                .route("card-service", predicateSpec -> predicateSpec
                        .path("/api/cards", "/api/sets", "/api/types", "/api/formats")
                        .uri("http://card-service:8080"))
                .route("user-service", predicateSpec -> predicateSpec
                        .path("/api/user/**")
                        .uri("http://user-service:8080"))
                .build();
    }
}
