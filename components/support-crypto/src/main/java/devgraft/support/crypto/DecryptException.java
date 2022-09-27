package devgraft.support.crypto;

import org.springframework.http.HttpStatus;

public class DecryptException extends CryptException {
    public DecryptException(String message, HttpStatus status) {
        super(message, status);
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException() {
        super("복호화에 실패하였습니다.");
    }
}
