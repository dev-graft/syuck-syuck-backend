package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AlreadyPostFriendException extends AbstractRequestException {
    public AlreadyPostFriendException() {
        super("이미 친구 요청 상태입니다.", StatusConstant.BAD_REQUEST);
    }
}
