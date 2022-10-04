package devgraft.auth.domain;

public class DecryptedSignInDataFixture {

    public static DecryptedSignInData.DecryptedSignInDataBuilder anData() {
        return DecryptedSignInData.builder()
                .loginId("loginId")
                .password("password")
                .pushToken("pushToken")
                .os("os")
                .deviceName("deviceName");
    }
}
