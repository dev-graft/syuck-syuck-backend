package devgraft.docs.auth;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.auth.api.AuthCodeIOUtils;
import devgraft.auth.api.SignInApi;
import devgraft.auth.app.SignInCodeService;
import devgraft.auth.app.SignInService;
import devgraft.support.crypto.KeyPairFixture;
import devgraft.support.crypto.RSA;
import devgraft.support.restdocs.AbstractApiDocTest;
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

import static devgraft.common.StrConstant.SIGN_IN_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(SignInApi.class)
class SignInApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private SignInCodeService signInCodeService;
    @MockBean
    private SignInService signInService;
    @MockBean
    private AuthCodeIOUtils authCodeIOUtils;

    @DisplayName("로그인-공개키 발급 요청")
    @Test
    void getSignInCode() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(signInCodeService.generatedSignInCode()).willReturn(KeyPairFixture.anKeyPair());

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-code")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("RSA 기반 로그인용 공개키")
                        )
                ));
    }

    @DisplayName("로그인")
    @Test
    void signIn() throws Exception {
        final KeyPair keyPair = RSA.generatedKeyPair();
        final String originPwd = "ZAqwerty123$";
        final String encryptPwd = RSA.encrypt(originPwd, keyPair.getPublic());
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_IN_KEY_PAIR, keyPair);
        final SignInService.EncryptedSignInRequest givenRequest = SignInService.EncryptedSignInRequest.builder()
                .loginId("qwerty123")
                .pushToken("PushToken")
                .os("web")
                .deviceName("DeviceName")
                .password(encryptPwd)
                .build();
        given(signInService.signIn(any(), any())).willReturn(new SignInService.SignInResult("A", "R"));


        mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest))
                        .session(mockHttpSession))
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("비밀번호(암호화)  원본: " + originPwd + "  공개키: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded())),
                                fieldWithPath("pushToken").type(JsonFieldType.STRING).description("Push 발송 토큰"),
                                fieldWithPath("os").type(JsonFieldType.STRING).description("android, ios, web"),
                                fieldWithPath("deviceName").type(JsonFieldType.STRING).description("장치 이름")
                        ),
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("읽을 필요 없는 값입니다.")
                        )
                ));
    }
}
