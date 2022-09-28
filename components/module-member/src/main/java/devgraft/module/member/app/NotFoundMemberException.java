package devgraft.module.member.app;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.module.member.domain.MemberConstant.NOT_FOUND_MEMBER;
import static devgraft.support.exception.StatusConstant.NOT_FOUND;

public class NotFoundMemberException extends AbstractRequestException {
    public NotFoundMemberException() {
        super(NOT_FOUND_MEMBER, NOT_FOUND);
    }
}
