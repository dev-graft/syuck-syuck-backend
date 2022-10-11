package devgraft.support.jwt;

import devgraft.common.wrap.ProcessOptional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private static final String JWT_UNIQ_ID = "uniqId";
    private static final String JWT_ACCESS_KEY = "access";

    private final Key signKey;
    public JwtProvider() {
        final String secret = Base64.getEncoder().encodeToString("sdAD@grdASFhjtGSFWE4@1@RFSDF23esa".getBytes(StandardCharsets.UTF_8));
        signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String issue(final JwtIssueRequest request) {
        final Claims claims = Jwts.claims();
        claims.putAll(request.getClaims());
        final Date nowDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(new Date(nowDate.getTime() + request.getPeriodSecond() * 1000))
                .signWith(signKey)
                .compact();
    }

    @Builder
    @Getter
    public static class JwtIssueRequest {
        private final Map<String, Object> claims;
        private final long periodSecond;
    }

    // JWT 검증 (검증된 정보 반환 / 언제 생성되었고, 어떤 값이 저장되어 있는지)
    public OldJwtVerifyResult verify(final JwtVerifyRequest request) throws JwtExpiredException, JwtValidationFailedException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();
        try {
            jwtParser.parseClaimsJws(request.getAccessToken());
            final Claims claims = jwtParser.parseClaimsJws(request.getRefreshToken()).getBody();
            if (!Objects.equals(claims.get(JWT_ACCESS_KEY), request.getAccessToken())) throw new JwtException("not compare");
            return new OldJwtVerifyResult((String)claims.get(JWT_UNIQ_ID));
        }catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (JwtException e) {
            throw new JwtValidationFailedException();
        }
    }

    public JwtVerifyResult verify(final String jwt) {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();
        try {
            final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);
            final Claims body = claimsJws.getBody();
            return new JwtVerifyResult(body, "Success", true);
        } catch (JwtException e) {
            return new JwtVerifyResult(null, e.getMessage(), false);
        }
    }

    @Getter
    public static class JwtVerifyResult extends ProcessOptional<Map<String, Object>> {
        private JwtVerifyResult(final Map<String, Object> value, final String message, final boolean success) {
            super(value, message, success);
        }
    }
}
