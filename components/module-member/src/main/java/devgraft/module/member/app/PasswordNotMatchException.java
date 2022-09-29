package devgraft.module.member.app;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.module.member.domain.MemberConstant.PASSWORD_NOT_MATCH;
import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

public class PasswordNotMatchException extends AbstractRequestException {
    public PasswordNotMatchException() {
        super(PASSWORD_NOT_MATCH, BAD_REQUEST);
    }
}
