package devgraft.member.api;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.support.exception.StatusConstant.NOT_FOUND;

public class NotFoundMemberException extends AbstractRequestException {
    public NotFoundMemberException() {
        super("요청한 회원 정보가 존재하지 않습니다.", NOT_FOUND);
    }
}
