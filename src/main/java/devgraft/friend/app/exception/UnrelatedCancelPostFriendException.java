package devgraft.friend.app.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedCancelPostFriendException extends AbstractRequestException {
    public UnrelatedCancelPostFriendException() {
        super("자신과 관계 없는 친구 요청 정보를 취소할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}
