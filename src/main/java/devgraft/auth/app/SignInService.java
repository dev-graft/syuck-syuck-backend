package devgraft.auth.app;


import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthenticateMemberResult;
import devgraft.auth.domain.DecryptedSignInData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class SignInService {
    private final SignInRequestDecoder signInRequestDecoder;
    private final AuthMemberService authMemberService;

    public void signIn(final EncryptedSignInRequest request, final KeyPair keyPair) {
        final DecryptedSignInData signInData = signInRequestDecoder.decrypt(request, keyPair);
        final AuthenticateMemberResult authResult = authMemberService.authenticate(signInData.toRequest());
        if (!authResult.isSuccess()) throw new SignInAuthenticationFailedException();
        // 인증 성공 시 SignInData 기반으로 인가정보 만들면 됨
    }
}
