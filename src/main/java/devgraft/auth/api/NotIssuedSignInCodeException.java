package devgraft.auth.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotIssuedSignInCodeException extends AbstractRequestException {
    public NotIssuedSignInCodeException() {
        super("로그인 요청 실패! 공개키 발급을 우선 진행해주세요.", BAD_REQUEST);
    }
}
