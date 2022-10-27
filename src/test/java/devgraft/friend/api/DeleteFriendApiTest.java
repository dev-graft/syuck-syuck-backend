package devgraft.friend.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.friend.app.DeleteFriendService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteFriendApiTest extends MemberCredentialsTestCase {
    private DeleteFriendService mockDeleteFriendService;

    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockDeleteFriendService = Mockito.mock(DeleteFriendService.class);
        return MockMvcBuilders.standaloneSetup(new DeleteFriendApi(mockDeleteFriendService));
    }

    @DisplayName("친구삭제 api ok status 확인")
    @Test
    void deleteFriend_returnOkHttpStatus() throws Exception {
        requestDeleteFriend()
                .andExpect(status().isOk());
    }

    @DisplayName("전달받은 정보 서비스에 전달")
    @Test
    void acceptPostFriend_passesInfoToService() throws Exception {
        final String givenMemberId = "gg";
        final long givenFriendRelationId = 10L;

        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestDeleteFriend(givenFriendRelationId)
                .andExpect(status().isOk());

        verify(mockDeleteFriendService, times(1)).deleteFriend(eq(givenMemberId), eq(givenFriendRelationId));
    }

    private ResultActions requestDeleteFriend() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
                .param("target", "1"));
    }

    private ResultActions requestDeleteFriend(final Long target) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
                .param("target", String.valueOf(target)));
    }
}