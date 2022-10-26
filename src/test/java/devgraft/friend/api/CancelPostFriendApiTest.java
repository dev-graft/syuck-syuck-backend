package devgraft.friend.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.friend.app.CancelPostFriendService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class CancelPostFriendApiTest extends MemberCredentialsTestCase {
    private CancelPostFriendService mockCancelPostFriendService;
    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockCancelPostFriendService = Mockito.mock(CancelPostFriendService.class);
        return standaloneSetup(new CancelPostFriendApi(mockCancelPostFriendService));
    }

    @DisplayName("친구요청 취소 ok status 확인")
    @Test
    void cancelPostFriend_returnOkHttpStatus() throws Exception {
        requestCancelPostFriend()
                .andExpect(status().isOk());
    }

    @DisplayName("전달받은 정보 서비스에 전달")
    @Test
    void cancelPostFriend_passesInfoToService() throws Exception {
        final String givenMemberId = "member";
        final long givenFriendRelationId = 10L;
        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestCancelPostFriend(givenFriendRelationId)
                .andExpect(status().isOk());

        verify(mockCancelPostFriendService, times(1)).cancelPostFriend(eq(givenMemberId), eq(givenFriendRelationId));
    }

    public ResultActions requestCancelPostFriend() throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/cancel")
                .param("target", String.valueOf(0)));
    }

    public ResultActions requestCancelPostFriend(final Long friendRelationId) throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/cancel")
                .param("target", String.valueOf(friendRelationId)));
    }
}