package devgraft.support.exception;

public class UnauthenticatedException extends AbstractRequestException {
    protected UnauthenticatedException(final String message) {
        super(message, ExceptionStatusConstant.UNAUTHENTICATED.getStatus());
    }

    protected UnauthenticatedException() {
        super(ExceptionStatusConstant.UNAUTHENTICATED);
    }
}
