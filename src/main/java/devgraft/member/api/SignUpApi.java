package devgraft.member.api;

import devgraft.common.JsonLogger;
import devgraft.member.app.EncryptedSignUpRequest;
import devgraft.member.app.SignUpCodeService;
import devgraft.member.app.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Optional;

import static devgraft.common.StrConstant.SIGN_UP_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignUpApi {
    private final SignUpCodeService signUpCodeService;
    private final SignUpService signUpService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-up")
    public String signUp(@RequestBody final EncryptedSignUpRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(SIGN_UP_KEY_PAIR))
                .orElseThrow(NotIssuedPublicKeyException::new);

        JsonLogger.logI(log, "SignUpApi.signUp \nPubKey: {} | PriKey: {} \nrequest: {}",
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()),
                request);

        signUpService.signUp(request, keyPair);

        return "Success";
    }

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-code")
    public String getSignUpCode(final HttpSession httpSession) {
        final KeyPair keyPair = signUpCodeService.generatedSignUpCode();
        httpSession.setAttribute(SIGN_UP_KEY_PAIR, keyPair);
        final String enKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        JsonLogger.logI(log, "SignUpApi.getPubKey Response: {}", enKey);

        return enKey;
    }
}