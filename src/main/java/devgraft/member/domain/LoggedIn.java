package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.security.KeyPair;
import java.util.Objects;

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

    public void compareToPassword(final MemberPasswordService decryptPasswordService, final String encryptedPwd, final KeyPair keyPair) {
        try {
            final String decryptPwd = decryptPasswordService.decryptPassword(encryptedPwd, keyPair);
            final String hash = decryptPasswordService.hashingPassword(decryptPwd);
            if (!Objects.equals(getPassword(), hash)) {
                throw new NotCorrectPasswordException();
            }
        } catch (final Exception e) {
            throw new NotCorrectPasswordException();
        }
    }
}

