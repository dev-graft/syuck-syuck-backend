package devgraft.apidoc;

import devgraft.member.api.MemberApi;
import devgraft.member.app.MemberIds;
import devgraft.member.app.MembershipService;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.support.crypt.RSA;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberApi.class)
public class MemberApiDoc extends AbstractApiDoc {
    @MockBean
    private MembershipService membershipService;
    @MockBean
    private MemberDataDao memberDataDao;

    @DisplayName("회원가입 요청")
    @Test
    void membership() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(RSA.KEY_PAIR, RSA.generatedKeyPair());
        given(membershipService.membership(any(), any())).willReturn(MemberIds.of(1L, "qwerty123"));
        KeyPair keyPair = RSA.generatedKeyPair();
        String originPassword = "qweR123$";
        String encryptPassword = RSA.encrypt(originPassword, keyPair.getPublic());

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "loginId": "qwerty123",
                                    "password": "%s",
                                    "nickname": "MEMBER",
                                    "profileImage": ""
                                }
                                """.formatted(encryptPassword)
                        ).session(mockHttpSession))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document.document(
                                requestFields(
                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호(암호화)  원본: " + originPassword + "  공개키: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded())),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                                ),
                                responseFields
                        )
                );
    }

    @DisplayName("아이디 존재 여부 요청")
    @Test
    void existsLoginId() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder().build()));

        mockMvc.perform(get("/api/members/check").param("loginId", "qweqwe123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("loginId").description("회원 아이디")
                                ),
                                responseFields.and(
                                        fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("아이디 존재 여부 결과(True=존재/False=존재안함)")
                                )
                        )
                );
    }

    @DisplayName("프로필 조회 요청")
    @Test
    void getMemberProfile() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder()
                        .loggedId("qwerty123")
                        .nickname("nic")
                        .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                        .stateMessage("좋은 날이에요!!")
                .build()));

        mockMvc.perform(get("/api/members/{loginId}", "qwerty123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                                pathParameters(
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
