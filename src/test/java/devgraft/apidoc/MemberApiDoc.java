package devgraft.apidoc;

import devgraft.member.api.MemberApi;
import devgraft.member.app.MemberIds;
import devgraft.member.app.MembershipService;
import devgraft.member.query.MemberQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberApi.class)
public class MemberApiDoc extends AbstractApiDoc {
    @MockBean
    private MembershipService membershipService;
    @MockBean
    private MemberQueryService memberQueryService;

    @DisplayName("회원가입 요청")
    @Test
    void membership() throws Exception {
        given(membershipService.membership(any(), any())).willReturn(MemberIds.of(1L, "memberId123"));

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "loginId": "memberId123",
                                    "password": "qwe123!@#",
                                    "nickname": "MEMBER",
                                    "profileImage": ""
                                }
                                """
                        ))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document.document(
                                requestFields(
                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("회원 아이디"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지").optional()
                                ),
                                responseFields
                        )
                );
    }
}
