package devgraft.support.exception;

import static devgraft.support.exception.ExceptionStatusConstant.ALREADY_EXISTS_ERROR;

public class AlreadyExistsException extends AbstractRequestException {
    protected AlreadyExistsException(final String message) {
        super(message, ALREADY_EXISTS_ERROR.getStatus());
    }

    protected AlreadyExistsException() {
        super(ALREADY_EXISTS_ERROR);
    }
}
