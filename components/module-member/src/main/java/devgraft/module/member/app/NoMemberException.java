package devgraft.module.member.app;

import devgraft.support.exception.NoContentException;

public class NoMemberException extends NoContentException {
    public NoMemberException() {
        super("요청한 회원이 존재하지 않습니다.");
    }
}
