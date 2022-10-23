package magicTheBuilder.ApiGateway.jwt;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${environments.jwt.token.secret}")
    private String secret;

    public Claims getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(String jwt) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
