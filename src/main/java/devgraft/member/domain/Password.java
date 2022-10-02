package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class Password {
    @Column(name = "password", nullable = false)
    private String value;

    public static Password from(final String value) {
        return new Password(value);
    }

    public boolean isMatch(final String password) {
        return this.value.equals(password);
    }
}

