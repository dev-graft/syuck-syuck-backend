package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MembershipDecryptedData;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class MembershipDecryptedDataProvider {
    public MembershipDecryptedData create(final MemberCryptService memberCryptService, final EncryptMembershipRequest request, final KeyPair keyPair) {
        final String decryptPassword = memberCryptService.decrypt(keyPair, request.getPassword());
        return new MembershipDecryptedData(request.getLoginId(), decryptPassword, request.getNickname(), request.getProfileImage());
    }
}
