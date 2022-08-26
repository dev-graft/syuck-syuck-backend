package devgraft.member.app;

import devgraft.support.exception.AbstractRequestException;

public class MemberPasswordDecryptFailedException extends AbstractRequestException {
    public MemberPasswordDecryptFailedException() {
        super("회원가입 요청이 실패하였습니다. 새로고침을 진행해주세요.");
    }
}
