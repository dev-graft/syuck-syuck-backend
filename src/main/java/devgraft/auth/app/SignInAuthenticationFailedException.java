package devgraft.auth.app;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

public class SignInAuthenticationFailedException extends AbstractRequestException {

    public SignInAuthenticationFailedException() {
        super("로그인 요청 실패! 아이디 및 비밀번호를 다시 확인해주세요.", BAD_REQUEST);
    }
}
