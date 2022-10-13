package devgraft.follow.api;

import devgraft.follow.app.AskFollowRequest;
import devgraft.follow.app.AskFollowService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowApiTest extends MemberCredentialsTestCase {
    private AskFollowService askFollowService;

    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        askFollowService = Mockito.mock(AskFollowService.class);
        return MockMvcBuilders.standaloneSetup(new FollowApi(askFollowService));
    }

    @DisplayName("팔로우 요청 status 200")
    @Test
    void askFollow_returnOkHttpStatus() throws Exception {
        final AskFollowRequest givenRequest = AskFollowRequest.builder().followerLoginId("dev").build();

        requestAskFollow(givenRequest)
                .andExpect(status().isOk());
    }

    @DisplayName("팔로우 요청문을 서비스에 전달")
    @Test
    void askFollow_passesAskFollowRequestToService() throws Exception {
        final AskFollowRequest givenRequest = AskFollowRequest.builder().followerLoginId("dev").build();

        requestAskFollow(givenRequest);

        verify(askFollowService, times(1)).askFollow(refEq("memberId"), refEq(givenRequest));
    }

    private ResultActions requestAskFollow(final AskFollowRequest givenRequest) throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(givenRequest)));
    }
}