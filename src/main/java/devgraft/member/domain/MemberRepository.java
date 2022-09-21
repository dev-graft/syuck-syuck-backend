package devgraft.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String loggedId);
    Optional<Member> findById(Long id);

    @Query(value = "" +
            " select m from Member m" +
            " where m.loggedIn.loggedId = :loggedId")
    Optional<Member> findByLoggedId(final String loggedId);

    Stream<Member> streamAllBy();

    Member save(Member member);
}
