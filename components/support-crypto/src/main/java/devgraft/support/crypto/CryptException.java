package devgraft.support.crypto;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class CryptException extends AbstractRequestException {
    public CryptException(final String message, final HttpStatus status) {
        super(message, status);
    }

    public CryptException(final String message) {
        super(message);
    }
}
