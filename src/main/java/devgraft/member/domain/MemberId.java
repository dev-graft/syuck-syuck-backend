package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberId implements Serializable {
    @Column(name = "member_id")
    private String id;

    @Override
    public boolean equals(Object o) {
        if (null == o || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return this == o || Objects.equals(id, memberId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getId() {
        return id;
    }

    public static MemberId from(final String id) {
        return new MemberId(id);
    }
}
