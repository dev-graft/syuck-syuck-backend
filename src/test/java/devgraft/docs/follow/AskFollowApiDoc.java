package devgraft.docs.follow;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.follow.api.AskFollowApi;
import devgraft.follow.app.AskFollowRequest;
import devgraft.follow.app.AskFollowService;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import javax.servlet.http.Cookie;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AskFollowApi.class)
public class AskFollowApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private AskFollowService askFollowService;

    @DisplayName("팔로우 요청")
    @Test
    void askFollow() throws Exception {
        final AskFollowRequest givenRequest = AskFollowRequest.builder()
                .followerLoginId("tom01")
                .build();

        mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(givenRequest))
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("followerLoginId").type(JsonFieldType.STRING).description("팔로우 대상 아이디")
                        )
                ))
        ;
    }
}
