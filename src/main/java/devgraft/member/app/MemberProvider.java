package devgraft.member.app;

import devgraft.common.ProfileImageProvider;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberCryptoService;
import devgraft.member.domain.MemberId;
import devgraft.member.domain.MemberStatus;
import devgraft.member.domain.Password;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberProvider {
    private final MemberCryptoService memberCryptoService;
    public Member create(final DecryptedSignUpData decryptedSignUpData) {
        final MemberId memberId = MemberId.from(decryptedSignUpData.getLoginId());
        final Password password = memberCryptoService.hashingPassword(decryptedSignUpData.getPassword());
        final String profileImage = StringUtils.isNotBlank(decryptedSignUpData.getProfileImage()) ?
                decryptedSignUpData.getProfileImage() : ProfileImageProvider.create();
        return Member.builder()
                .id(memberId)
                .password(password)
                .nickname(decryptedSignUpData.getNickname())
                .profileImage(profileImage)
                .stateMessage("")
                .status(MemberStatus.N)
                .build();
    }
}
