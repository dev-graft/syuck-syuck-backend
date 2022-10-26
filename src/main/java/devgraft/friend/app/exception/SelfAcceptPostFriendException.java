package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class SelfAcceptPostFriendException extends AbstractRequestException {
    public SelfAcceptPostFriendException() {
        super("친구 요청자가 승인할 수 없습니다.", StatusConstant.BAD_REQUEST);
    }
}
