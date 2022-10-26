package devgraft.friend.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotFoundFriendRelationException extends AbstractRequestException {
    public NotFoundFriendRelationException() {
        super(StatusConstant.NOT_FOUND);
    }
}
