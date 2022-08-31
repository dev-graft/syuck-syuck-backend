package devgraft.member.app;

import devgraft.support.exception.DecryptException;

public class LoginDecryptFailedException extends DecryptException {
    public LoginDecryptFailedException() {
        super("로그인 요청이 실패하였습니다. 새로고침을 진행해주세요.");
    }
}
