package devgraft.auth.app;

public class EncryptedSignInRequestFixture {
    public static EncryptedSignInRequest.EncryptedSignInRequestBuilder anRequest() {
        return EncryptedSignInRequest.builder()
                .loginId("loginId")
                .password("password");
    }
}
