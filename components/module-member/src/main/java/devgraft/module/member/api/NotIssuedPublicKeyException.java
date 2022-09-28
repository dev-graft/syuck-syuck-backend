package devgraft.module.member.api;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.module.member.domain.MemberConstant.NOT_ISSUED_PUBLIC_KEY;
import static devgraft.support.exception.StatusConstant.BAD_REQUEST;

public class NotIssuedPublicKeyException extends AbstractRequestException {
    public NotIssuedPublicKeyException() {
        super(NOT_ISSUED_PUBLIC_KEY, BAD_REQUEST);
    }
}
