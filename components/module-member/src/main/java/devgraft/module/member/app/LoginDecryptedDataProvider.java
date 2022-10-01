package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class LoginDecryptedDataProvider {
    public LoginDecryptedData create(final MemberCryptService memberCryptService, final EncryptLoginRequest request, final KeyPair keyPair) {
        final String decryptedPassword = memberCryptService.decrypt(keyPair, request.getPassword());
        return new LoginDecryptedData(request.getLoginId(), decryptedPassword);
    }
}
