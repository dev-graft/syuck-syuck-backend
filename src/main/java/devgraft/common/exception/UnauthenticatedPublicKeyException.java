package devgraft.common.exception;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.support.exception.StatusConstant.UNAUTHENTICATED;

public class UnauthenticatedPublicKeyException extends AbstractRequestException {
    public UnauthenticatedPublicKeyException() {
        super("복호화 실패! 공개키가 올바르지 않습니다.", UNAUTHENTICATED);
    }
}
