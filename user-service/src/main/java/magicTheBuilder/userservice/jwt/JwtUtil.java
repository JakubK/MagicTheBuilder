package magicTheBuilder.userservice.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import magicTheBuilder.userservice.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${environments.jwt.token.secret}")
    private String secret;

    @Value("${environments.jwt.token.time}")
    private long timeUntilExpiration;

    public String createToken(User user) {
        Claims claims = Jwts.claims();
        if (user == null) {  // JUST FOR DEVELOPMENT PURPOSES. SHOULD BE REMOVED AFTER DB SYNC WITH DECK-SERV WORKS
            claims.put("id", 1000000L);
        } else {
            claims.put("id", user.getId());
        }
        long validFrom = System.currentTimeMillis();
        long validTo = validFrom + (timeUntilExpiration * 60000);
        Date exp = new Date(validTo);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(validFrom)).setExpiration(exp).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
