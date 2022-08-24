package devgraft.member.query;

import devgraft.member.domain.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberData {
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private String profileImage;
    private String stateMessage;
    private MemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
