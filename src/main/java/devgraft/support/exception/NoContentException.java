package devgraft.support.exception;

import org.springframework.http.HttpStatus;

public class NoContentException extends AbstractRequestException {
    public NoContentException(final String message) {
        super(message, HttpStatus.NO_CONTENT);
    }
}
