package devgraft.member.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberId {
    private final Long idx;
    private final String id;

    public static MemberId of(final Long idx, final String id) {
        return new MemberId(idx, id);
    }
}
