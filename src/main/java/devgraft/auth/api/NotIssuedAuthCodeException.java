package devgraft.auth.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotIssuedAuthCodeException extends AbstractRequestException {
    public NotIssuedAuthCodeException() {
        super("로그인 인증정보 갱신 실패! 로그인 인증정보가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
