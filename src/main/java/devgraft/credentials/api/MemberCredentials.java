package devgraft.credentials.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberCredentials {
    private final String memberId;
    private final String os;
    private final String pushToken;
    private final String deviceName;
}
