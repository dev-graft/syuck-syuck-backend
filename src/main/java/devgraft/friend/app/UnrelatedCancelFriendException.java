package devgraft.friend.app;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class UnrelatedCancelFriendException extends AbstractRequestException {
    public UnrelatedCancelFriendException() {
        super("자신과 관계 없는 친구 요청 정보를 취소할 수 없습니다.", StatusConstant.UNRELATED_REQUEST_ERROR);
    }
}
