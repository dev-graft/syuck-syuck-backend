package devgraft.member.app;

import devgraft.member.domain.MemberCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class SignUpCodeService {
    private final MemberCryptoService memberCryptoService;

    public KeyPair generatedSignUpCode() {
        return memberCryptoService.generatedCryptKey();
    }
}
