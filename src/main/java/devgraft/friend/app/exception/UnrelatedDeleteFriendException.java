package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedDeleteFriendException extends AbstractRequestException {
    public UnrelatedDeleteFriendException() {
        super("자신과 관계 없는 친구관계를 삭제 할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}
