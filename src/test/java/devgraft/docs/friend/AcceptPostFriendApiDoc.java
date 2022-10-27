package devgraft.docs.friend;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentialsResolver;
import devgraft.friend.api.AcceptPostFriendApi;
import devgraft.friend.app.AcceptPostFriendService;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;

import javax.servlet.http.Cookie;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AcceptPostFriendApi.class)
public class AcceptPostFriendApiDoc extends AbstractApiDocTest {
    @MockBean
    private AcceptPostFriendService acceptPostFriendService;
    @MockBean
    protected AuthCodeFilter authCodeFilter;
    @MockBean
    protected MemberCredentialsResolver memberCredentialsResolver;

    @DisplayName("친구 요청 수락")
    @Test
    void acceptPostFriend() throws Exception {
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());

        mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/accept")
                        .param("target", "1")
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("target").description("친구관계 아이디(Long)")
                        ),
                        responseFields
                ))
        ;
    }
}
