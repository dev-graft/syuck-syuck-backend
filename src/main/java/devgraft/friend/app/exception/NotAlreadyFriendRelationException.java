package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotAlreadyFriendRelationException extends AbstractRequestException {
    public NotAlreadyFriendRelationException() {
        super("아직 친구 관계가 아닙니다.", StatusConstant.BAD_REQUEST);
    }
}
