package devgraft.module.member.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDecryptedData {
    private final String loginId;
    private final String password;
}
