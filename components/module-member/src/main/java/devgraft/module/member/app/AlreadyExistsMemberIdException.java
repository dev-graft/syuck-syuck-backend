package devgraft.module.member.app;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.module.member.domain.MemberConstant.ALREADY_EXISTS_MEMBER_ID;
import static devgraft.support.exception.ExceptionStatusCode.ALREADY_EXISTS_ERROR;

public class AlreadyExistsMemberIdException extends AbstractRequestException {
    public AlreadyExistsMemberIdException() {
        super(ALREADY_EXISTS_MEMBER_ID, ALREADY_EXISTS_ERROR.getCode());
    }
}
