package devgraft.auth.api;

import devgraft.auth.app.EncryptedSignInRequest;
import devgraft.auth.app.SignInCodeService;
import devgraft.auth.app.SignInResult;
import devgraft.auth.app.SignInService;
import devgraft.common.JsonLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Optional;

import static devgraft.common.StrConstant.SIGN_IN_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignInApi {
    private final SignInCodeService signInCodeService;
    private final SignInService signInService;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-code")
    public String getSignInCode(final HttpSession httpSession) {
        final KeyPair keyPair = signInCodeService.generateSignInCode();
        httpSession.setAttribute(SIGN_IN_KEY_PAIR, keyPair);
        final String enKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        JsonLogger.logI(log, "SignInApi.getPubKey Response: {}", enKey);

        return enKey;
    }

    @PostMapping(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-in")
    public void signIn(@RequestBody final EncryptedSignInRequest request, final HttpSession httpSession, final HttpServletResponse response) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(SIGN_IN_KEY_PAIR))
                .orElseThrow(NotIssuedSignInCodeException::new);
        httpSession.removeAttribute(SIGN_IN_KEY_PAIR);
        JsonLogger.logI(log, "SignInApi.signIn Request: {}", request);

        final SignInResult signInResult = signInService.signIn(request, keyPair);

        response.addHeader("ACCESS-TOKEN", signInResult.getAccessToken());
        Cookie cookie = new Cookie("REFRESH-TOKEN", signInResult.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
