package devgraft.support.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtIssueRequest {
    private final String uniqId;

    public static JwtIssueRequest of(final String uniqId) {
        Assert.notNull(uniqId, "uniqId must not be null");
        return new JwtIssueRequest(uniqId);
    }
}
