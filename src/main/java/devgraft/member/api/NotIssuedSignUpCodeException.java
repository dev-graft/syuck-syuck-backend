package devgraft.member.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotIssuedSignUpCodeException extends AbstractRequestException {
    public NotIssuedSignUpCodeException() {
        super("회원가입 요청 실패! 공개키 발급을 우선 진행해주세요.", BAD_REQUEST);
    }
}
