package devgraft.follow.api;


import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.follow.app.CancelFollowService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CancelFollowApiTest extends MemberCredentialsTestCase {
    private CancelFollowService mockCancelFollowService;

    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockCancelFollowService = Mockito.mock(CancelFollowService.class);
        return MockMvcBuilders.standaloneSetup(new CancelFollowApi(mockCancelFollowService));
    }

    @DisplayName("팔로우 취소 status 200")
    @Test
    void cancelFollow_returnOkHttpStatus() throws Exception {
        requestCancelFollow("followId")
                .andExpect(status().isOk());
    }

    @DisplayName("팔로우 취소 대상의 아이디를 서비스에 전달")
    @Test
    void cancelFollow_passesIdToService() throws Exception {
        final String givenMemberId = "CancelTestId";
        final String givenFollowId = "GFollowId";
        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestCancelFollow(givenFollowId);

        verify(mockCancelFollowService, times(1)).cancelFollow(refEq(givenMemberId), refEq(givenFollowId));
    }

    private ResultActions requestCancelFollow(final String targetId) throws Exception {
        return mockMvc.perform(delete(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX)
                .param("fId", targetId));
    }
}