package devgraft.module.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPatterns {
    public static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^(?=.*[a-z])[a-z|A-Z0-9]{5,20}$");
    public static final Pattern PASSWORD_ID_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()/?~])[a-z|A-Z0-9!@#$%^&*()/?~]{8,25}$");
    public static final Pattern NICKNAME_ID_PATTERN = Pattern.compile("^[\\w|가-힣ㄱ-ㅎㅏ-ㅣ]{1,10}$");
}
