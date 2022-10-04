package devgraft.auth.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthSessionProvider {
    private static final String version = "v1";

    public AuthSession create(final DecryptedSignInData data) {
        return AuthSession.builder()
                .uniqId(UUID.randomUUID().toString())
                .memberId(data.getLoginId())
                .version(version)
                .pushToken(data.getPushToken())
                .os(data.getOs())
                .deviceName(data.getDeviceName())
                .block(false)
                .build();
    }
}
