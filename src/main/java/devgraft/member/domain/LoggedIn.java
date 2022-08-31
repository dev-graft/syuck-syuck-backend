package devgraft.member.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class LoggedIn {
    private String loggedId;
    private String password;

    protected LoggedIn() {
    }
    private LoggedIn(final String loggedId, final String password) {
        this.loggedId = loggedId;
        this.password = password;
    }

    public static LoggedIn of(final String loggedId, final String password) {
        return new LoggedIn(loggedId, password);
    }
    public String getLoggedId() {
        return loggedId;
    }

    public String getPassword() {
        return password;
    }
}

