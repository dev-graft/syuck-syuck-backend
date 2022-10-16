package devgraft.member.api;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.member.app.UpdateMemberProfileRequest;
import devgraft.member.app.UpdateMemberProfileService;
import devgraft.support.testcase.MemberCredentialsTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateMemberProfileApiTest extends MemberCredentialsTestCase {
    private UpdateMemberProfileService mockUpdateMemberProfileService;
    @Override
    protected StandaloneMockMvcBuilder getStandaloneMockMvcBuilder() {
        mockUpdateMemberProfileService = mock(UpdateMemberProfileService.class);
        return MockMvcBuilders.standaloneSetup(new UpdateMemberProfileApi(mockUpdateMemberProfileService));
    }

    @DisplayName("프로필 업데이트 요청 status=200")
    @Test
    void updateProfile_returnOkHttpStatus() throws Exception {
        final UpdateMemberProfileRequest givenRequest = new UpdateMemberProfileRequest("DevGraft", "업데이트");

        requestUpdateProfile(givenRequest)
                .andExpect(status().isOk());
    }

    @DisplayName("프로필 업데이트 요청문 서비스 전달")
    @Test
    void updateProfile_passesRequestToService() throws Exception {
        final String givenMemberId = "givenId";
        final UpdateMemberProfileRequest givenRequest = new UpdateMemberProfileRequest("DevGraft", "업데이트");
        setGivenMemberCredentials(MemberCredentialsFixture.anCredentials().memberId(givenMemberId).build());

        requestUpdateProfile(givenRequest);

        verify(mockUpdateMemberProfileService, times(1)).update(refEq(givenMemberId), refEq(givenRequest));
    }

    public ResultActions requestUpdateProfile(final UpdateMemberProfileRequest givenRequest) throws Exception {
        return mockMvc.perform(put(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/profile")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getObjectMapper().writeValueAsString(givenRequest)));
    }
}
