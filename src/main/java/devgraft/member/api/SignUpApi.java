package devgraft.member.api;

import devgraft.member.app.EncryptedSignUpRequest;
import devgraft.member.app.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Optional;

import static devgraft.common.StrConstant.KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class SignUpApi {
    private final SignUpService signUpService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-up")
    public String signUp(@RequestBody final EncryptedSignUpRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(KEY_PAIR))
                .orElseThrow(NotIssuedPublicKeyException::new);

        signUpService.signUp(request, keyPair);

        return "Success";
    }
}
