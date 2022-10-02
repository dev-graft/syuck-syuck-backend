package devgraft.member.app;

public class EncryptedSignUpRequestFixture {

    public static EncryptedSignUpRequest.EncryptedSignUpRequestBuilder anRequest() {
        return EncryptedSignUpRequest.builder()
                .loginId("loginId")
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage");
    }
}
