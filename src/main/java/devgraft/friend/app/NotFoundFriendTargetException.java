package devgraft.friend.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotFoundFriendTargetException extends AbstractRequestException {
    public NotFoundFriendTargetException() {
        super("요청 대상을 찾지 못했습니다.", StatusConstant.NOT_FOUND);
    }

}
