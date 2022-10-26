package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedRefusePostFriendException  extends AbstractRequestException {
    public UnrelatedRefusePostFriendException() {
        super("자신과 관계 없는 친구 요청을 거절 할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}