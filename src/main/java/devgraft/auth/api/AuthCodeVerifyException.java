package devgraft.auth.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class AuthCodeVerifyException extends AbstractRequestException {
    public AuthCodeVerifyException() {
        super("인증정보가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
