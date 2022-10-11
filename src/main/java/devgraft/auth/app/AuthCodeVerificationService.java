package devgraft.auth.app;

import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthorizationElements;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthCodeVerificationService {
    private final AuthCodeService authCodeService;
    @Transactional(readOnly = true)
    public String verify(final AuthorizationElements authorizationElements) {
        // accessToken 만료 검증
        authCodeService.expired(authorizationElements.getAccessToken());
        // refresh 검증(키쌍, 만료)
        return authCodeService.verify(authorizationElements).getUniqId();
    }
}
