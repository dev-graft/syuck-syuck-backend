package devgraft.code.api;

import devgraft.member.domain.MemberPasswordService;
import devgraft.support.crypt.MD5;
import devgraft.support.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Base64;

@RequiredArgsConstructor
@RequestMapping("api/code")
@RestController
public class CodeApi {
    private static final int COUNT = 12;
    private final MemberPasswordService memberPasswordService;

    @GetMapping
    public InitCodeResult initCode(HttpSession httpSession) {
        final KeyPair keyPair = memberPasswordService.generateCryptoKey();
        httpSession.setAttribute(MemberPasswordService.KEY_PAIR, keyPair);
        return new InitCodeResult(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    }

    @GetMapping("profile")
    public SingleResult<String> getDefaultProfileImage() {
        String encrypt = MD5.encrypt(RandomStringUtils.random(COUNT));
        return SingleResult.success("https://secure.gravatar.com/avatar/"+ encrypt + "?s=800&d=identicon");
    }
}
