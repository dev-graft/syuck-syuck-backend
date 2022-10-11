package devgraft.auth.app;

import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthCodeService.AuthCodeExpiredResult;
import devgraft.auth.domain.AuthorizationElements;
import devgraft.common.wrap.ProcessOptional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthCodeVerificationService {
    private final AuthCodeService authCodeService;

    @Transactional(readOnly = true)
    public ProcessOptional<String> verify(final AuthorizationElements authorizationElements) {
        // accessToken 만료 검증
        final AuthCodeExpiredResult expiredResult = authCodeService.expired(authorizationElements.getAccessToken());
        if (!expiredResult.isSuccess()) return new ProcessOptional<>(authorizationElements.getAccessToken(), "요청정보가 만료되었습니다. 갱신해주세요.", false);
        // refresh 검증(키쌍, 만료)
        final String uniqId = authCodeService.verify(authorizationElements).orElseThrow();
        // uniqId 반환
        return new ProcessOptional<>(uniqId, "Success", true);
    }
}
