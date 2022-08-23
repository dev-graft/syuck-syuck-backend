package devgraft.apidoc;

import devgraft.member.api.MemberApi;
import devgraft.member.app.MemberIds;
import devgraft.member.app.MembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberApi.class)
public class MemberApiDoc {
    @MockBean
    private MembershipService membershipService;
    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider, WebApplicationContext context) {
        document = MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(document)
                .build();
    }

    @DisplayName("회원가입 Api 요청")
    @Test
    void membership() throws Exception {
        given(membershipService.membership(any())).willReturn(MemberIds.of(1L, "memberId123"));

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
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("결과 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("결과 시간"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("인덱스"),
                                        fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("아이디")
                                        )
                        )
                );
    }
}
