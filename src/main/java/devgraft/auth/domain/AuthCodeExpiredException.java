package devgraft.auth.domain;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AuthCodeExpiredException extends AbstractRequestException {
    public AuthCodeExpiredException() {
        super("접속정보가 만료되었습니다. 갱신 또는 로그인을 시도해주세요.", StatusConstant.UNAUTHENTICATED);
    }
}
