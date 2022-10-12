package devgraft.docs.member;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentialsResolver;
import devgraft.member.api.UpdateMemberProfileApi;
import devgraft.member.app.UpdateMemberProfileRequest;
import devgraft.member.app.UpdateMemberProfileService;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({UpdateMemberProfileApi.class})
public class UpdateMemberProfileApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private UpdateMemberProfileService updateMemberProfileService;
    @MockBean
    private MemberCredentialsResolver memberCredentialsResolver;

    @DisplayName("프로필 업데이트 요청")
    @Test
    void updateMemberProfile() throws Exception {
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());

        final UpdateMemberProfileRequest givenRequest = new UpdateMemberProfileRequest("devgraft", "슉슉 환영!");
        mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/profile")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest)))
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("stateMessage").type(JsonFieldType.STRING).description("상태 메세지")
                        ),
                        responseFields
                ));
    }
}
