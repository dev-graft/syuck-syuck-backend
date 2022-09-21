package devgraft.support.crypt;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class CryptException extends AbstractRequestException {
    public CryptException(final String message, final HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public CryptException(final String message) {
        super(message);
    }
}
