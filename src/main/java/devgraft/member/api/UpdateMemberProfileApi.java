package devgraft.member.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.member.app.UpdateMemberProfileRequest;
import devgraft.member.app.UpdateMemberProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class UpdateMemberProfileApi {
    private final UpdateMemberProfileService updateMemberProfileService;

    @PutMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/profile")
    public void updateProfile(@Credentials final MemberCredentials memberCredentials, @RequestBody final UpdateMemberProfileRequest request) {
        updateMemberProfileService.update(memberCredentials.getMemberId(), request);
    }
}
