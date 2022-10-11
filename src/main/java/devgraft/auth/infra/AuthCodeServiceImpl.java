package devgraft.auth.infra;

import devgraft.auth.domain.AuthCodeExpiredException;
import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthCodeValidationFailedException;
import devgraft.auth.domain.AuthorizationElements;
import devgraft.support.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthCodeServiceImpl implements AuthCodeService {
    private final JwtProvider jwtProvider;
    private static final String JWT_UNIQ_ID = "uniqId";
    private static final String JWT_ACCESS_KEY = "access";

    @Override
    public AuthCodeGeneratedResult generate(final String uniqId) {
        final String accessToken = jwtProvider.issue(new HashMap<>(), 600L);

        final Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_UNIQ_ID, uniqId);
        claims.put(JWT_ACCESS_KEY, accessToken);

        final String refreshToken = jwtProvider.issue(claims, 2592000L);

        return new AuthCodeGeneratedResult(accessToken, refreshToken);
    }

    @Override
    public void expired(final String code) {
        try {
            jwtProvider.verify(code);
        } catch (ExpiredJwtException e) {
            throw new AuthCodeExpiredException();
        } catch (JwtException e) {
            throw new AuthCodeValidationFailedException();
        }
    }

    @Override
    public AuthCodeVerifyResult verify(final AuthorizationElements authorizationElements) {
        try {
            final Map<String, Object> claims = jwtProvider.verify(authorizationElements.getRefreshToken());
            if (!Objects.equals(authorizationElements.getAccessToken(), claims.get(JWT_ACCESS_KEY)))
                throw new AuthCodeValidationFailedException();
            final String uniqId = (String) claims.get(JWT_UNIQ_ID);
            return new AuthCodeVerifyResult(uniqId);
        } catch (ExpiredJwtException e) {
            throw new AuthCodeExpiredException();
        } catch (JwtException e) {
            throw new AuthCodeValidationFailedException();
        }
    }
}
