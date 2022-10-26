package devgraft.friend.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedAcceptFriendException extends AbstractRequestException {
    public UnrelatedAcceptFriendException() {
        super("자신과 관계 없는 친구요청은 처리할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}