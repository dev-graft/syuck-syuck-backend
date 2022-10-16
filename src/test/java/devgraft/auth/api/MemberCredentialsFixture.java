package devgraft.auth.api;

import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentials.MemberCredentialsBuilder;

public class MemberCredentialsFixture {
    public static MemberCredentialsBuilder anCredentials() {
        return MemberCredentials.builder()
                .memberId("memberId")
                .deviceName("deviceId")
                .os("os")
                .pushToken("pushToken")
                .version("v1");
    }
}
