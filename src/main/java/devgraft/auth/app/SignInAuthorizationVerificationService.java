package devgraft.auth.app;

import devgraft.auth.domain.AuthorizationElements;
import devgraft.common.wrap.ProcessOptional;
import org.springframework.transaction.annotation.Transactional;

public class SignInAuthorizationVerificationService {

    @Transactional(readOnly = true)
    public ProcessOptional<String> verify(final AuthorizationElements authorizationElements) {
        // accessToken 만료 검증
        // refresh 검증(키쌍, 만료)
        // uniqId 반환
        return null;
    }
}
