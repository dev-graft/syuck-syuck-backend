package devgraft.support.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionStatusConstant {
    ALREADY_EXISTS_ERROR("요청 정보가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("요청한 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND);


    private final String message;
    private final HttpStatus status;

    ExceptionStatusConstant(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
