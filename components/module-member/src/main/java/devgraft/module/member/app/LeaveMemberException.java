package devgraft.module.member.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class LeaveMemberException extends AbstractRequestException {

    public LeaveMemberException() {
        super("탈퇴된 회원정보입니다. 복구 요청이 필요할 경우 문의 바랍니다.", StatusConstant.FORBIDDEN);
    }
}
