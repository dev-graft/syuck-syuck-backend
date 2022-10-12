package devgraft.common.credential;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberCredentials {
    private final String memberId;
    private final String version;
    private final String pushToken;
    private final String os;
    private final String deviceName;
}
