package devgraft.module.member.app;

import devgraft.support.exception.NotFoundException;

import static devgraft.module.member.domain.MemberConstant.NOT_FOUND_MEMBER;

public class NotFoundMemberException extends NotFoundException {
    public NotFoundMemberException() {
        super(NOT_FOUND_MEMBER);
    }
}
