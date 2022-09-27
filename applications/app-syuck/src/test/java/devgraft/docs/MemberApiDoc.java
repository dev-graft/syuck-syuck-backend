package devgraft.docs;

import devgraft.module.member.api.MemberApi;
import devgraft.module.member.app.EncryptMembershipRequest;
import devgraft.module.member.app.MembershipService;
import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.query.MemberDataDao;
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

import static devgraft.module.member.api.MemberApi.KEY_PAIR;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberApi.class)
class MemberApiDoc extends AbstractApiDocTest {
    @MockBean
    private MembershipService membershipService;
    @MockBean
    private MemberCryptService memberCryptService;
    @MockBean
    private MemberDataDao memberDataDao;

    @DisplayName("회원가입 요청 doc")
    @Test
    void membership() throws Exception {
        final KeyPair keyPair = RSA.generatedKeyPair();
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(KEY_PAIR, keyPair);
        final EncryptMembershipRequest givenRequest = new EncryptMembershipRequest(
                "qwerty123", "ZAqwerty123$", "nickname", "profileImage"
        );

        mockMvc.perform(post("/api/members/membership")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest))
                        .session(mockHttpSession))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호(암호화)  원본: " + givenRequest.getPassword() + "  공개키: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded())),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                        ),
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("읽을 필요 없는 값입니다.")
                        )
                ));
    }
}
