package devgraft.follow.domain;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AlreadyFollowingException extends AbstractRequestException {
    public AlreadyFollowingException() {
        super("이미 팔로우 상태입니다.", StatusConstant.BAD_REQUEST);
    }
}
