package devgraft.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByMemberId(final String memberId);
    Stream<Follow> streamAllByMemberId(final String memberId);
    Optional<Follow> findByMemberIdAndFollowingMemberId(final String memberId, final String followingMemberId);
}
