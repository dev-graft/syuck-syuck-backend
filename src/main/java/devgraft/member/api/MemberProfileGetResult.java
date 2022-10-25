package devgraft.member.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MemberProfileGetResult {
    private final String loginId;
    private final String nickname;
    private final String profileImage;
    private final String stateMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Boolean self;
}
