package devgraft.support.exception.simple;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AlreadyExistsException extends AbstractRequestException {
    public AlreadyExistsException() {
        super(StatusConstant.ALREADY_EXISTS_ERROR);
    }

    public AlreadyExistsException(final String message) {
        super(message, StatusConstant.ALREADY_EXISTS_ERROR);
    }
}
