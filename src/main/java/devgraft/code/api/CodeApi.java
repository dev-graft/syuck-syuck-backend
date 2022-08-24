package devgraft.code.api;

import devgraft.support.crypt.RSA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RequestMapping("api/code")
@RestController
public class CodeApi {

    @GetMapping
    public InitCodeResult initCode(HttpSession httpSession) throws NoSuchAlgorithmException {
        final KeyPair keyPair = RSA.generatedKeyPair();
        httpSession.setAttribute(RSA.KEY_PAIR, keyPair);
        return new InitCodeResult(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    }
}
