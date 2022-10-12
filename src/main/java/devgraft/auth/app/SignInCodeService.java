package devgraft.auth.app;

import devgraft.auth.domain.SignInCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class SignInCodeService {
    private final SignInCryptoService signInCryptoService;

    public KeyPair generatedSignInCode() {
        return signInCryptoService.generatedCode();
    }
}
