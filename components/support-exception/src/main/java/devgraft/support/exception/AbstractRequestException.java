package devgraft.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractRequestException extends RuntimeException {
    private final int httpStatus;
    private final String message;

    protected AbstractRequestException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus.value();
        this.printStackTrace();
    }

    protected AbstractRequestException(final String message, final int httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.printStackTrace();
    }

    protected AbstractRequestException(final String message) {
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
        this.printStackTrace();
    }
}