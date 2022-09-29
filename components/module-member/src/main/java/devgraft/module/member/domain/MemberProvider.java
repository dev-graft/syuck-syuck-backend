package devgraft.module.member.domain;

import devgraft.module.member.app.ProfileImageProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MemberProvider {

    public Member create(final MemberCryptService memberCryptService, final MembershipDecryptedData decryptedData) {
        final MemberId memberId = MemberId.from(decryptedData.getLoginId());
        final Password password = memberCryptService.hashingPassword(decryptedData.getPassword());
        final String profileImage = StringUtils.isNotBlank(decryptedData.getProfileImage()) ?
                decryptedData.getProfileImage() : ProfileImageProvider.create();
        return Member.builder()
                .id(memberId)
                .password(password)
                .nickname(decryptedData.getNickname())
                .profileImage(profileImage)
                .stateMessage("")
                .status(MemberStatus.N)
                .build();
    }
}
