package devgraft.friend.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.friend.app.RefusePostFriendService;
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

class RefusePostFriendApiTest extends MemberCredentialsTestCase {
    private RefusePostFriendService mockRefusePostFriendService;
    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockRefusePostFriendService = Mockito.mock(RefusePostFriendService.class);

        return MockMvcBuilders.standaloneSetup(new RefusePostFriendApi(mockRefusePostFriendService));
    }

    @DisplayName("친구 요청 거절 ok status 확인")
    @Test
    void refusePostFriend_returnOkHttpStatus() throws Exception {
        requestRefusePostFriend()
                .andExpect(status().isOk());
    }

    @DisplayName("전달받은 정보 서비스에 전달")
    @Test
    void refusePostFriend_passesInfoToService() throws Exception {
        final String givenMemberId = "gg";
        final long givenFriendRelationId = 10L;

        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestRefusePostFriend(givenFriendRelationId)
                .andExpect(status().isOk());

        verify(mockRefusePostFriendService, times(1)).refusePostFriend(eq(givenMemberId), eq(givenFriendRelationId));
    }

    private ResultActions requestRefusePostFriend() throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/refuse")
                .param("target", "1"));
    }

    private ResultActions requestRefusePostFriend(final Long target) throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/refuse")
                .param("target", String.valueOf(target)));
    }
}