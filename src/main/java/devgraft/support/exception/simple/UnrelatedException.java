package devgraft.support.exception.simple;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedException extends AbstractRequestException {
    public UnrelatedException() {
        super(StatusConstant.UNRELATED_REQUEST_ERROR);
    }

    public UnrelatedException(final String message) {
        super(message, StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}
