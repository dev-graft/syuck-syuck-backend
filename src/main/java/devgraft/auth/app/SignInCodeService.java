package devgraft.auth.app;

import devgraft.auth.domain.AuthCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class SignInCodeService {
    private final AuthCryptoService authCryptoService;

    public KeyPair generateSignInCode() {
        return authCryptoService.generatedCryptoKey();
    }
}
