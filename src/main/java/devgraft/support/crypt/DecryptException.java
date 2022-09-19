package devgraft.support.crypt;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class DecryptException extends AbstractRequestException {
    public DecryptException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException() {
        super("복호화에 실패하였습니다.");
    }
}
