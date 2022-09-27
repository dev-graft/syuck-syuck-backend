package devgraft.module.member.domain;

import devgraft.support.exception.UnauthenticatedException;

import static devgraft.module.member.domain.MemberConstant.UNAUTHENTICATED_PUBLIC_KEY;

public class UnauthenticatedPublicKeyException extends UnauthenticatedException {
    public UnauthenticatedPublicKeyException() {
        super(UNAUTHENTICATED_PUBLIC_KEY);
    }
}
