package devgraft.friend.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.friend.app.PostFriendService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PostFriendApiTest extends MemberCredentialsTestCase {
    private PostFriendService mockPostFriendService;
    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockPostFriendService = Mockito.mock(PostFriendService.class);
        return MockMvcBuilders.standaloneSetup(new PostFriendApi(mockPostFriendService));
    }

    @DisplayName("친구요청 api ok status 확인")
    @Test
    void postFriend_returnOkHttpStatus() throws Exception {
        requestPostFriend()
                .andExpect(status().isOk());
    }

    @DisplayName("전달받은 정보 서비스에 전달")
    @Test
    void postFriend_passesInfoToService() throws Exception {
        final String givenMemberId = "mmm";
        final String givenTarget = "ttt";

        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestPostFriend(givenTarget)
                .andExpect(status().isOk());

        verify(mockPostFriendService, Mockito.times(1)).postFriend(givenMemberId, givenTarget);
    }

    private ResultActions requestPostFriend() throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
                .param("target", "targetId"));
    }

    private ResultActions requestPostFriend(final String target) throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
                .param("target", target));
    }
}