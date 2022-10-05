package devgraft.auth.app;

public class EncryptedSignInRequestFixture {
    public static SignInService.EncryptedSignInRequest.EncryptedSignInRequestBuilder anRequest() {
        return SignInService.EncryptedSignInRequest.builder()
                .loginId("qwerty123")
                .password("Qwerty123$")
                .pushToken("pushToken")
                .os("os")
                .deviceName("deviceName");
    }
}
