package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class LoggedIn {//implements Serializable {
    private String loggedId;
    private String password;

    public static LoggedIn of(final String loggedId, final String password) {
        return new LoggedIn(loggedId, password);
    }

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (null == o || getClass() != o.getClass()) return false;
//        final LoggedIn loggedIn = (LoggedIn) o;
//        return Objects.equals(id, loggedIn.id) &&
//                Objects.equals(password, loggedIn.password);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, password);
//    }
}

