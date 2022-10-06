package devgraft.member.domain.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class AlreadyExistsMemberIdException extends AbstractRequestException {
    public AlreadyExistsMemberIdException() {
        super("이미 존재하는 아이디입니다.", StatusConstant.ALREADY_EXISTS_ERROR);
    }
}
