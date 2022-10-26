package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedAcceptPostFriendException extends AbstractRequestException {
    public UnrelatedAcceptPostFriendException() {
        super("자신과 관계 없는 친구요청은 처리할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}
