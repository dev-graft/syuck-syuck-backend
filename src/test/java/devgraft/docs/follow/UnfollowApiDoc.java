package devgraft.docs.follow;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentialsResolver;
import devgraft.follow.api.UnfollowApi;
import devgraft.follow.app.AskFollowRequest;
import devgraft.follow.app.UnfollowService;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;

import javax.servlet.http.Cookie;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.UNFOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(UnfollowApi.class)
public class UnfollowApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private UnfollowService unfollowService;
    @MockBean
    private MemberCredentialsResolver memberCredentialsResolver;

    @DisplayName("팔로우 취소")
    @Test
    void unfollow() throws Exception {
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());

        final AskFollowRequest givenRequest = AskFollowRequest.builder()
                .followMemberId("tom01")
                .build();

        mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + UNFOLLOW_URL_PREFIX)
                        .param("target", "tom01")
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("target").description("팔로우 취소 대상 아이디")
                        ),
                        responseFields
                ))
        ;
    }
}