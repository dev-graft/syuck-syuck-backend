package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Access(AccessType.FIELD)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class LoggedIn implements Serializable {
    private String loggedId;
    private String password;

    public static LoggedIn of(final String loggedId, final String password) {
        return new LoggedIn(loggedId, password);
    }
}

