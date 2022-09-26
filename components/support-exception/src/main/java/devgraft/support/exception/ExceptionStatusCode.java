package devgraft.support.exception;

public enum ExceptionStatusCode {
    ALREADY_EXISTS_ERROR(-441);


    private final int code;

    ExceptionStatusCode(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
