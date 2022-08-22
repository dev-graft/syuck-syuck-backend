package devgraft.member.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    boolean existsById(String id);
    Optional<Member> findByIdx(Long idx);
    void save(Member member);
}
