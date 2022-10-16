package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FindMemberIdsService {
    private final MemberRepository memberRepository;

    public List<FindMemberResult> findMembers(final List<String> memberIds) {
        return memberRepository.streamAllBy()
                .filter(member -> memberIds.contains(member.getMemberId()))
                .filter(member -> !member.isLeave())
                .map(FindMemberResult::from)
                .collect(Collectors.toList());
    }

    @Builder(access = AccessLevel.PUBLIC)
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @Getter
    public static class FindMemberResult {
        private final String memberId;
        private final String nickname;
        private final String profileImage;
        private final String stateMessage;

        public static FindMemberResult from(final Member member) {
            return builder()
                    .memberId(member.getMemberId())
                    .nickname(member.getNickname())
                    .profileImage(member.getProfileImage())
                    .stateMessage(member.getStateMessage())
                    .build();
        }
    }
}
