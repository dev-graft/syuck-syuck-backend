package devgraft.member.app;

import devgraft.member.domain.MemberCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@RequiredArgsConstructor
@Component
public class SignUpRequestDecoder {
    private final MemberCryptoService memberCryptoService;

    public DecryptedSignUpData decrypt(final EncryptedSignUpRequest request, final KeyPair keyPair) {
        final String decryptedPassword = memberCryptoService.decrypt(keyPair, request.getPassword());
        return new DecryptedSignUpData(request.getLoginId(), decryptedPassword, request.getNickname(), request.getProfileImage());
    }
}
