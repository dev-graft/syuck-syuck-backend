package devgraft.follow.domain;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class NotFoundFollowTargetException extends AbstractRequestException {
    public NotFoundFollowTargetException() {
        super("요청 대상을 찾지 못했습니다.", StatusConstant.NOT_FOUND);
    }
}
