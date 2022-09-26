package devgraft.module.member.api;

import devgraft.module.member.app.EncryptMembershipRequest;
import devgraft.module.member.app.MembershipService;
import devgraft.module.member.domain.MemberCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Optional;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    public static final String KEY_PAIR = "key_pair";
    private final MembershipService membershipService;
    private final MemberCryptService memberCryptService;


    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("membership")
    public void membership(@RequestBody final EncryptMembershipRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(KEY_PAIR)).orElseThrow(NotFindCodeException::new);
        membershipService.membership(request, keyPair);
    }


    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @GetMapping("code")
    public String getPubKey(HttpSession httpSession) {
        final KeyPair keyPair = memberCryptService.generatedCryptKey();
        httpSession.setAttribute(KEY_PAIR, keyPair);
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }
}
