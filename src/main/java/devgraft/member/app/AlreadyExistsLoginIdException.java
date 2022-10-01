package devgraft.member.app;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsLoginIdException extends AbstractRequestException {
    public AlreadyExistsLoginIdException() {
        super("이미 존재하는 아이디입니다.", HttpStatus.PAYMENT_REQUIRED);
    }
}
