package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@RequiredArgsConstructor
@Component
public class DecryptMembershipRequestProvider {
    private final MemberCryptService memberCryptService;
    public DecryptMembershipRequest from(final EncryptMembershipRequest request, final KeyPair keyPair) {
        final String decryptPassword = memberCryptService.decrypt(keyPair, request.getPassword());
        return new DecryptMembershipRequest(request.getLoginId(), decryptPassword, request.getNickname(), request.getProfileImage());
    }
}
