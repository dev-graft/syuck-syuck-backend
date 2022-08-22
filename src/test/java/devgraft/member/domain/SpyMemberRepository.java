package devgraft.member.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SpyMemberRepository implements MemberRepository {
    public Long nextIdx = 1L;
    public final Map<Long, Member> data = new HashMap<>();

    @Override
    public boolean existsById(String id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Member> findByIdx(Long idx) {
        return Optional.ofNullable(data.get(nextIdx));
    }

    @Override
    public void save(Member member) {
        if (null == member.getIdx()) {
            member.setIdx(nextIdx);
            data.put(nextIdx, member);
            nextIdx++;
        } else {
            data.put(member.getIdx(), member);
        }
    }
}
