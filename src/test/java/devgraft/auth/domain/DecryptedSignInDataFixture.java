package devgraft.auth.domain;

import devgraft.auth.app.SignInRequestDecoder;

public class DecryptedSignInDataFixture {

    public static SignInRequestDecoder.DecryptedSignInData.DecryptedSignInDataBuilder anData() {
        return SignInRequestDecoder.DecryptedSignInData.builder()
                .loginId("loginId")
                .password("password")
                .pushToken("pushToken")
                .os("os")
                .deviceName("deviceName");
    }
}
