package devgraft.auth.app;

import devgraft.auth.app.SignInRequestDecoder.DecryptedSignInData;
import devgraft.auth.domain.AccessStatusType;
import devgraft.auth.domain.AuthSession;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthSessionProvider {
    // TODO export properties
    private static final String VERSION = "v1";

    public AuthSession create(final DecryptedSignInData data) {
        return AuthSession.builder()
                .uniqId(UUID.randomUUID().toString())
                .memberId(data.getLoginId())
                .version(VERSION)
                .pushToken(data.getPushToken())
                .os(data.getOs())
                .deviceName(data.getDeviceName())
                .block(false)
                .accessStatus(AccessStatusType.OFFLINE)
                .build();
    }
}
