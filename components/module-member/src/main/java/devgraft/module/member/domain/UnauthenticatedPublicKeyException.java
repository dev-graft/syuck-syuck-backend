package devgraft.module.member.domain;

import devgraft.support.exception.AbstractRequestException;

import static devgraft.module.member.domain.MemberConstant.UNAUTHENTICATED_PUBLIC_KEY;
import static devgraft.support.exception.StatusConstant.UNAUTHENTICATED;

public class UnauthenticatedPublicKeyException extends AbstractRequestException {
    public UnauthenticatedPublicKeyException() {
        super(UNAUTHENTICATED_PUBLIC_KEY, UNAUTHENTICATED);
    }
}
