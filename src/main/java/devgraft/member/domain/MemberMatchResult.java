package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberMatchResult {
    private final String message;
    private final boolean success;

    public static MemberMatchResult of(final String message, final boolean success) {
        return new MemberMatchResult(message, success);
    }
}
