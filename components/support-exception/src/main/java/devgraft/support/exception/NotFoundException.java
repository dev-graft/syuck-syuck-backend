package devgraft.support.exception;

import static devgraft.support.exception.ExceptionStatusConstant.NOT_FOUND;

public class NotFoundException extends AbstractRequestException {
    protected NotFoundException(final String message) {
        super(message, NOT_FOUND.getStatus());
    }

    protected NotFoundException() {
        super(NOT_FOUND);
    }
}
