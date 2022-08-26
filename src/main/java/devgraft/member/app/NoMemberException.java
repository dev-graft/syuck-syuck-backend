package devgraft.member.app;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NoMemberException extends AbstractRequestException {
    public NoMemberException() {
        super("요청하신 회원이 존재하지 않습니다.", HttpStatus.PAYMENT_REQUIRED);
    }
}
