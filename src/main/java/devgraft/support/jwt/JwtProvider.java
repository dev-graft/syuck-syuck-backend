package devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private static final String SECRET = "sdAD@grdASFhjtGSFWE4@1@RFSDF23esa";
    private static final String JWT_UNIQ_ID = "uniqId";
    private static final String JWT_ACCESS_KEY = "access";

    private final Key signKey;

    public JwtProvider() {
        final String secret = Base64.getEncoder().encodeToString(SECRET.getBytes(StandardCharsets.UTF_8));
        signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String issue(final Map<String, Object> data, final long periodSecond) {
        final Claims claims = Jwts.claims();
        claims.putAll(data);
        final Date nowDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(new Date(nowDate.getTime() + periodSecond * 1000))
                .signWith(signKey)
                .compact();
    }

    public Map<String, Object> verify(final String jwt) throws JwtException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);
        return claimsJws.getBody();
    }
}
