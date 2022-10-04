package devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final Key signKey;
    public JwtProvider() {
        final String secret = Base64.getEncoder().encodeToString("DKJQFNSKDLNH@IU".getBytes(StandardCharsets.UTF_8));
        signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    // JWT 발급
    public JwtIssuedResult issue(final JwtIssueRequest request) {
        final String accessToken = _issue(Jwts.claims(), 600L);
        final Claims refreshClaims = Jwts.claims();
        refreshClaims.put("uniqId", request.getUniqId());
        final String refreshToken = _issue(refreshClaims, 2592000L);
        return JwtIssuedResult.of(accessToken, refreshToken);
    }

    // JWT 검증 (검증된 정보 반환 / 언제 생성되었고, 어떤 값이 저장되어 있는지)
    public JwtVerifyResult verify(JwtVerifyRequest request) throws JwtExpiredException, JwtValidationFailedException {
//        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();
//        try {
//            jwtParser.parseClaimsJws(request.getAccessToken())
//        }catch (ExpiredJwtException e) {
//            throw new JwtExpiredException();
//        } catch (JwtException e) {
//
//        }
        // accessToken 만료 검증
//        throw new JwtExpiredException();
        // access & refresh 일치하는지 검증
//        throw new JwtValidationFailedException();
        return null;
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
