package devgraft.auth.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class BlockAuthSessionException extends AbstractRequestException {
    public BlockAuthSessionException() {
        super("차단된 접근정보입니다.", StatusConstant.UNAUTHENTICATED);
    }
}
