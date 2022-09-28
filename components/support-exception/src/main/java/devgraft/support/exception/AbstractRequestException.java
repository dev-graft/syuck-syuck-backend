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

    protected AbstractRequestException(final String message, final StatusConstant constant) {
        super(message);
        this.message = message;
        this.status = constant.getStatus();
        this.printStackTrace();
    }

    protected AbstractRequestException() {
        super(StatusConstant.BAD_REQUEST.getMessage());
        this.message = StatusConstant.BAD_REQUEST.getMessage();
        this.status = StatusConstant.BAD_REQUEST.getStatus();
        this.printStackTrace();
    }

    protected AbstractRequestException(final String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.printStackTrace();
    }

    protected AbstractRequestException(final StatusConstant constant) {
        super(constant.getMessage());
        this.message = constant.getMessage();
        this.status = constant.getStatus();
        this.printStackTrace();
    }
}