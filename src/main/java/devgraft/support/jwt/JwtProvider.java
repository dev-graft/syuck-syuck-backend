package devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    // JWT 발급
    public JwtIssuedResult issue(final JwtIssueRequest request) {
        final String accessToken = _issue(Jwts.claims(), 600L); //10분
        final Claims refreshClaims = Jwts.claims();
        refreshClaims.put(JWT_UNIQ_ID, request.getUniqId());
        refreshClaims.put(JWT_ACCESS_KEY, accessToken);
        final String refreshToken = _issue(refreshClaims, 2592000L); //30일
        return JwtIssuedResult.of(accessToken, refreshToken);
    }

    // JWT 검증 (검증된 정보 반환 / 언제 생성되었고, 어떤 값이 저장되어 있는지)
    public JwtVerifyResult verify(JwtVerifyRequest request) throws JwtExpiredException, JwtValidationFailedException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();
        try {
            jwtParser.parseClaimsJws(request.getAccessToken());
            final Claims claims = jwtParser.parseClaimsJws(request.getRefreshToken()).getBody();
            if (!Objects.equals(claims.get(JWT_ACCESS_KEY), request.getAccessToken())) throw new JwtException("not compare");
            return new JwtVerifyResult((String)claims.get(JWT_UNIQ_ID));
        }catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (JwtException e) {
            throw new JwtValidationFailedException();
        }
    }

    private String _issue(final Claims claims, final Long periodSecond) {
        final Date nowDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(new Date(nowDate.getTime() + periodSecond * 1000))
                .signWith(signKey)
                .compact();
    }
}
