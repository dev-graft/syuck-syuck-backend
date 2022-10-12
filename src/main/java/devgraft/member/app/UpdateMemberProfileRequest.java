package devgraft.member.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateMemberProfileRequest {
    private String nickname;
    private String stateMessage;
}
