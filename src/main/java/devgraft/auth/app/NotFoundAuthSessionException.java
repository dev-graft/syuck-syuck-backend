package devgraft.auth.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotFoundAuthSessionException extends AbstractRequestException {
    public NotFoundAuthSessionException() {
        super("인증정보가 존재하지 않습니다. 로그인을 요청해주세요.", StatusConstant.UNAUTHENTICATED);
    }
}
