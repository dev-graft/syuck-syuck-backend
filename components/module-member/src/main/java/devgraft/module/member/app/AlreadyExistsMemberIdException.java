package devgraft.module.member.app;

import devgraft.support.exception.AlreadyExistsException;

import static devgraft.module.member.domain.MemberConstant.ALREADY_EXISTS_MEMBER_ID;

public class AlreadyExistsMemberIdException extends AlreadyExistsException {
    public AlreadyExistsMemberIdException() {
        super(ALREADY_EXISTS_MEMBER_ID);
    }
}
