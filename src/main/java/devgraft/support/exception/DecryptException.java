package devgraft.support.exception;

import org.springframework.http.HttpStatus;

public class DecryptException extends AbstractRequestException {
    public DecryptException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException() {
        super("패스워드 복호화에 실패하였습니다. 새로고침을 진행해주세요");
    }
}
