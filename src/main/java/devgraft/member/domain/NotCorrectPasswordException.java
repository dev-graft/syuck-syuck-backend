package devgraft.member.domain;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotCorrectPasswordException extends AbstractRequestException {
    public NotCorrectPasswordException() {
        super("패스워드 검증이 실패하였습니다.", HttpStatus.OK);
    }
}
