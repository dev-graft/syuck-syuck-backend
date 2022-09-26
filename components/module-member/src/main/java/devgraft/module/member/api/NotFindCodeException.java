package devgraft.module.member.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotFindCodeException extends AbstractRequestException {
    public NotFindCodeException() {
        super("code must not be null.", HttpStatus.UNAUTHORIZED);
    }
}
