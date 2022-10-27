package devgraft.auth.api;

import devgraft.auth.app.SignInService;
import devgraft.auth.domain.AuthorizationElements;
import devgraft.auth.domain.SignInCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;

@RequiredArgsConstructor
@RestController
public class AutoLoginApi {
    private final SignInCryptoService signInCryptoService;
    private final SignInService signInService;
    private final AuthCodeIOUtils authCodeIOUtils;

    @GetMapping(API_PREFIX + AUTH_URL_PREFIX + "/atl")
    public String signIn(final HttpServletResponse response, @RequestParam(value = "target", defaultValue = "qwerty123") final String target) {
        final String pwd = "Qwerty12#";

        final KeyPair keyPair = signInCryptoService.generatedCode();
        final String encryptPwd = signInCryptoService.encrypt(pwd, keyPair);
        final AuthorizationElements signInResult = signInService.signIn(SignInService.EncryptedSignInRequest.builder()
                .loginId(target)
                .password(encryptPwd)
                .build(), keyPair);
        authCodeIOUtils.injectAuthorizationCode(response, signInResult);
        return "Success";
    }
}
