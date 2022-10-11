package devgraft.auth.infra;

import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthorizationElements;
import devgraft.common.wrap.ProcessResult;
import devgraft.support.jwt.JwtProvider;
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
        final String accessToken = jwtProvider.issue(JwtProvider.JwtIssueRequest.builder().periodSecond(600L).build());

        final Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_UNIQ_ID, uniqId);
        claims.put(JWT_ACCESS_KEY, accessToken);

        final String refreshToken = jwtProvider.issue(JwtProvider.JwtIssueRequest.builder()
                .claims(claims).periodSecond(2592000L).build());

        return new AuthCodeGeneratedResult(accessToken, refreshToken);
    }

    @Override
    public AuthCodeExpiredResult expired(final String code) {
        final ProcessResult verify = jwtProvider.verify(code);
        return new AuthCodeExpiredResult(verify.getMessage(), verify.isSuccess());
    }

    @Override
    public AuthCodeVerifyResult verify(final AuthorizationElements authorizationElements) {
        final JwtProvider.JwtVerifyResult jwtVerify = jwtProvider.verify(authorizationElements.getRefreshToken());
        if (!jwtVerify.isSuccess()) return new AuthCodeVerifyResult(null, jwtVerify.getMessage(), jwtVerify.isSuccess());
        final Map<String, Object> claims = jwtVerify.orElseThrow();
        if (!Objects.equals(authorizationElements.getAccessToken(), claims.get(JWT_ACCESS_KEY))) return new AuthCodeVerifyResult(null, "not compare", false);
        final String uniqId = (String) claims.get(JWT_UNIQ_ID);
        return new AuthCodeVerifyResult(uniqId, "Success", true);
    }
}
