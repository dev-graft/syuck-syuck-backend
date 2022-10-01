package devgraft.support.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtGenerateRequest {
    private final String sub;
    private final String aud;
    private final String role;
    private final long periodSecond;

    public static JwtGenerateRequest of(final String sub, final String aud, final String role, final long periodSecond) {
        Assert.notNull(aud, "sub(subject) must not be null");
        Assert.notNull(aud, "aud(audience) must not be null");
        Assert.notNull(role, "role must not be null");
        return new JwtGenerateRequest(sub, aud, role, periodSecond);
    }
}
