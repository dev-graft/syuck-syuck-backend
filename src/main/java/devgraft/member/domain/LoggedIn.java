package devgraft.member.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class LoggedIn {
    private String loggedId;
    private String password;

    public LoggedIn() {
    }

    public LoggedIn(String loggedId, String password) {
        this.loggedId = loggedId;
        this.password = password;
    }

    public String getLoggedId() {
        return loggedId;
    }

    public String getPassword() {
        return password;
    }
}

