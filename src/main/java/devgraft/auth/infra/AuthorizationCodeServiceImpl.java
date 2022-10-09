package devgraft.auth.infra;

import devgraft.auth.domain.AuthorizationCodeService;
import devgraft.support.jwt.JwtIssueRequest;
import devgraft.support.jwt.JwtIssuedResult;
import devgraft.support.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {
    private final JwtProvider jwtProvider;
    @Override
    public SignInAuthorizationResult generate(final String uniqId) {
        final JwtIssuedResult issue = jwtProvider.issue(JwtIssueRequest.of(uniqId));
        return new SignInAuthorizationResult(issue.getAccessToken(), issue.getRefreshToken());
    }
}
