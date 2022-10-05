package devgraft.docs;

import devgraft.auth.api.SignInApi;
import devgraft.auth.app.EncryptedSignInRequestFixture;
import devgraft.auth.app.SignInCodeService;
import devgraft.auth.app.SignInService;
import devgraft.support.crypto.KeyPairFixture;
import devgraft.support.crypto.RSA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import java.security.KeyPair;
import java.util.Base64;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({SignInApi.class})
public class AuthApiDoc extends AbstractApiDocTest {
    @MockBean
    private SignInService signInService;
    @MockBean
    private SignInCodeService signInCodeService;

    @DisplayName("로그인-공개키 발급 요청")
    @Test
    void getSignInCode() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(signInCodeService.generateSignInCode()).willReturn(KeyPairFixture.anKeyPair());

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-code")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("RSA 기반 로그인용 공개키")
                        )
                ));
    }

    @DisplayName("로그인 요청")
    @Test
    void signIn() throws Exception {
        final KeyPair keyPair = RSA.generatedKeyPair();
        final String originPwd = "Qwerty123$";
        final String encryptPwd = RSA.encrypt(originPwd, keyPair.getPublic());

        SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest()
                .password(encryptPwd)
                .build();

        mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest)))
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("비밀번호(암호화)  원본: " + originPwd + "  공개키: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded())),
                                fieldWithPath("pushToken").type(JsonFieldType.STRING).description("로그인한 장치에게 push를 전달할 수 있는 토큰.(FCM)"),
                                fieldWithPath("os").type(JsonFieldType.STRING).description("web, android, ios"),
                                fieldWithPath("deviceName").type(JsonFieldType.STRING).description("web-브라우저 명\nmobile-기기 명")
                        )
                ));
    }
}
