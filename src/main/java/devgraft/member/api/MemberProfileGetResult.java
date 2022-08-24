package devgraft.member.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class MemberProfileGetResult {
    private String loginId;
    private String nickname;
    private String profileImage;
    private String stateMessage;
    private LocalDateTime createdAt;
}
