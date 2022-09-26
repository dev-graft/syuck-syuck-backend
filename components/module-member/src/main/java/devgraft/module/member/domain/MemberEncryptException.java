package devgraft.module.member.domain;

import devgraft.support.crypto.EncryptException;

public class MemberEncryptException extends EncryptException {
    public MemberEncryptException(final String message) {
        super(message);
    }
}
