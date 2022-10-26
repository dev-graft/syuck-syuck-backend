package devgraft.friend.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.friend.app.AcceptFriendService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AcceptFriendApiTest extends MemberCredentialsTestCase {
    private AcceptFriendService mockAcceptFriendService;
    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockAcceptFriendService = Mockito.mock(AcceptFriendService.class);
        return MockMvcBuilders.standaloneSetup(new AcceptFriendApi(mockAcceptFriendService));
    }

    @DisplayName("친구 요청 수락 ok status 확인")
    @Test
    void acceptFriend_returnOkHttpStatus() throws Exception {
        requestAcceptFriend()
                .andExpect(status().isOk());
    }

    @DisplayName("전달받은 정보 서비스에 전달")
    @Test
    void acceptFriend_passesInfoToService() throws Exception {
        final String givenMemberId = "gg";
        final long givenFriendRelationId = 10L;

        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestAcceptFriend(givenFriendRelationId)
                .andExpect(status().isOk());

        verify(mockAcceptFriendService, times(1)).acceptFriend(eq(givenMemberId), eq(givenFriendRelationId));
    }

    private ResultActions requestAcceptFriend() throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/accept")
                .param("target", "0"));
    }

    private ResultActions requestAcceptFriend(final long target) throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/accept")
                .param("target", String.valueOf(target)));
    }
}