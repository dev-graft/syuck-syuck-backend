package devgraft.docs;

import devgraft.member.api.MemberQueryApi;
import devgraft.member.api.SignUpApi;
import devgraft.member.app.EncryptedSignUpRequest;
import devgraft.member.app.SignUpService;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Optional;

import static devgraft.common.StrConstant.SIGN_UP_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({SignUpApi.class, MemberQueryApi.class})
class MemberApiDoc extends AbstractApiDocTest {
    @MockBean
    private SignUpService signUpService;
    @MockBean
    private MemberDataDao memberDataDao;

    @DisplayName("회원가입-공개키 발급 요청")
    @Test
    void getSignUpCode() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(signUpService.generatedSignUpCode()).willReturn(KeyPairFixture.anKeyPair());

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-code")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("RSA 기반 회원가입용 공개키")
                        )
                ));
    }

    @DisplayName("아이디 존재 여부 확인 요청")
    @Test
    void isExistsLoginId() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder().build()));

        mockMvc.perform(RestDocumentationRequestBuilders.get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/exists")
                        .param("loginId", "qwerty123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("loginId").description("회원 아이디")
                        ),
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("아이디 존재 여부 결과(True=존재/False=존재안함)")
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
                .andDo(print())
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

    @DisplayName("회원 프로필 요청")
    @Test
    void getMemberProfile() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder()
                .memberId("qwerty123")
                .nickname("nic")
                .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                .stateMessage("좋은 날이에요!!")
                .build()));

        mockMvc.perform(RestDocumentationRequestBuilders.get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX)
                        .param("loginId", "qwerty123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("loginId").description("회원 아이디")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                        fieldWithPath("data.stateMessage").type(JsonFieldType.STRING).description("상태 메세지")
                                )
                        )
                );
    }
}
