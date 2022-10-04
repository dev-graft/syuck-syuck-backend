package devgraft.auth.domain;

public class AuthSessionFixture {
    public static AuthSession.AuthSessionBuilder anAuthSession() {
        return AuthSession.builder()
                .uniqId("uniqId")
                .memberId("loginId")
                .pushToken("pushToken")
                .os("os")
                .version("v1")
                .deviceName("deviceName")
                .block(false);
    }
}
