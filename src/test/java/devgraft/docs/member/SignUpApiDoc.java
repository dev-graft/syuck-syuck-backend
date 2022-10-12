package devgraft.docs.member;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.member.api.SignUpApi;
import devgraft.member.app.EncryptedSignUpRequest;
import devgraft.member.app.SignUpCodeService;
import devgraft.member.app.SignUpService;
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

import static devgraft.common.StrConstant.SIGN_UP_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({SignUpApi.class})
public class SignUpApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private SignUpCodeService signUpCodeService;
    @MockBean
    private SignUpService signUpService;

    @DisplayName("회원가입-공개키 발급 요청")
    @Test
    void getSignUpCode() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(signUpCodeService.generatedSignUpCode()).willReturn(KeyPairFixture.anKeyPair());

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-code")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("RSA 기반 회원가입용 공개키")
                        )
                ));
    }

    @DisplayName("회원가입 요청")
    @Test
    void signUp() throws Exception {
        final KeyPair keyPair = RSA.generatedKeyPair();
        final String originPwd = "ZAqwerty123$";
        final String encryptPwd = RSA.encrypt(originPwd, keyPair.getPublic());
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_UP_KEY_PAIR, keyPair);
        final EncryptedSignUpRequest givenRequest = new EncryptedSignUpRequest(
                "qwerty123", encryptPwd, "nickname", "profileImage"
        );

        mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest))
                        .session(mockHttpSession))
                .andExpect(status().isCreated())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호(암호화)  원본: " + originPwd + "  공개키: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded())),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                        ),
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("읽을 필요 없는 값입니다.")
                        )
                ));
    }
}
