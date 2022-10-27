package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class SelfPostFriendException extends AbstractRequestException {
    public SelfPostFriendException() {
        super("자신에게 친구를 요청할 수 없습니다.", StatusConstant.BAD_REQUEST);
    }
}
