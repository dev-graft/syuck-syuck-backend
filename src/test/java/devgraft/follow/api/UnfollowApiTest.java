package devgraft.follow.api;


import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.follow.app.UnfollowService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.UNFOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnfollowApiTest extends MemberCredentialsTestCase {
    private UnfollowService mockUnfollowService;

    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockUnfollowService = Mockito.mock(UnfollowService.class);
        return MockMvcBuilders.standaloneSetup(new UnfollowApi(mockUnfollowService));
    }

    @DisplayName("언팔로우 status 200")
    @Test
    void unfollow_returnOkHttpStatus() throws Exception {
        requestCancelFollow("followId")
                .andExpect(status().isOk());
    }

    @DisplayName("언팔로우 대상의 아이디를 서비스에 전달")
    @Test
    void unfollow_passesIdToService() throws Exception {
        final String givenMemberId = "CancelTestId";
        final String givenFollowId = "GFollowId";
        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestCancelFollow(givenFollowId);

        verify(mockUnfollowService, times(1)).unfollow(refEq(givenMemberId), refEq(givenFollowId));
    }

    private ResultActions requestCancelFollow(final String targetId) throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + UNFOLLOW_URL_PREFIX)
                .param("target", targetId));
    }
}