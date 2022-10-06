package devgraft.member.domain.exception;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.StatusConstant;

public class MemberEncryptException extends AbstractRequestException {
    public MemberEncryptException() {
        super("정확한 공개키를 입력해주세요.", StatusConstant.BAD_REQUEST);
    }
}
