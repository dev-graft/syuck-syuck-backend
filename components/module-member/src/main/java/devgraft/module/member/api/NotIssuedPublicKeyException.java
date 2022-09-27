package devgraft.module.member.api;

import devgraft.support.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

import static devgraft.module.member.domain.MemberConstant.NOT_ISSUED_PUBLIC_KEY;

public class NotIssuedPublicKeyException extends AbstractRequestException {
    public NotIssuedPublicKeyException() {
        super(NOT_ISSUED_PUBLIC_KEY, HttpStatus.BAD_REQUEST);
    }
}
