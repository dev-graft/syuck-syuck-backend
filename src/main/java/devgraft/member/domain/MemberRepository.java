package devgraft.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
    Stream<Member> streamAllBy();
}
