package devgraft.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String loggedId);
    Optional<Member> findById(Long id);

    Stream<Member> streamAllBy();

    Member save(Member member);
}
