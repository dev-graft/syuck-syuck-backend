package devgraft.friend.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AlreadyFriendRelationException extends AbstractRequestException {
    public AlreadyFriendRelationException() {
        super("이미 친구 관계 입니다.", StatusConstant.BAD_REQUEST);
    }
}
