package devgraft.member.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotIssuedPublicKeyException extends AbstractRequestException {
    public NotIssuedPublicKeyException() {
        super("공개키 발급을 우선 진행해주세요.", BAD_REQUEST);
    }
}
