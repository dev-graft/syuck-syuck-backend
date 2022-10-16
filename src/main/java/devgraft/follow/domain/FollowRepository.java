package devgraft.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowerId(final String followerId);
    Stream<Follow> streamAllByFollowerId(final String followerId);
    Optional<Follow> findByFollowerIdAndFollowingId(final String followerId, final String followingId);

}
