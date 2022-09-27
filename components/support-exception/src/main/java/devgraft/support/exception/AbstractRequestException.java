package devgraft.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractRequestException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    protected AbstractRequestException(final String message, final HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
        this.printStackTrace();
    }

    protected AbstractRequestException() {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.message = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.status = HttpStatus.BAD_REQUEST;
        this.printStackTrace();
    }

    protected AbstractRequestException(final String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.printStackTrace();
    }

    protected AbstractRequestException(final ExceptionStatusConstant constant) {
        super(constant.getMessage());
        this.message = constant.getMessage();
        this.status = constant.getStatus();
        this.printStackTrace();
    }
}