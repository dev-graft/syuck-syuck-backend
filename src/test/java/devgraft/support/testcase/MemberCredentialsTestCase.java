package devgraft.support.testcase;

import devgraft.auth.api.MemberCredentialsFixture;
import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentialsResolver;
import org.mockito.Mockito;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public abstract class MemberCredentialsTestCase extends StandaloneMockMvcTestCase {
    private MemberCredentialsResolver mockMemberCredentialsResolver;

    public void setGivenMemberCredentials(final MemberCredentials memberCredentials) {
        given(mockMemberCredentialsResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(memberCredentials);
    }

    @Override
    protected List<HandlerMethodArgumentResolver> getCustomArgumentResolvers() {
        final List<HandlerMethodArgumentResolver> superR = super.getCustomArgumentResolvers();
        mockMemberCredentialsResolver = Mockito.mock(MemberCredentialsResolver.class);
        given(mockMemberCredentialsResolver.supportsParameter(any())).willReturn(true);
        given(mockMemberCredentialsResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(MemberCredentialsFixture.anCredentials().build());

        superR.add(mockMemberCredentialsResolver);

        return superR;
    }
}
