package devgraft.support.exception.simple;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotFoundException extends AbstractRequestException {
    public NotFoundException() {
        super(StatusConstant.NOT_FOUND);
    }
}
