package devgraft.follow.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class SelfFollowException extends AbstractRequestException {

    public SelfFollowException() {
        super("잘못된 요청입니다.", StatusConstant.BAD_REQUEST);
    }
}
