package devgraft.auth.domain;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class AuthCodeValidationFailedException extends AbstractRequestException {
    public AuthCodeValidationFailedException() {
        super("인증정보가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
