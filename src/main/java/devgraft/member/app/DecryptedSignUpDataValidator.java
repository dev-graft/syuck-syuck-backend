package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static devgraft.member.domain.MemberPatterns.LOGIN_ID_PATTERN;
import static devgraft.member.domain.MemberPatterns.NICKNAME_ID_PATTERN;
import static devgraft.member.domain.MemberPatterns.PASSWORD_ID_PATTERN;

@Component
public class DecryptedSignUpDataValidator {

    public List<ValidationError> validate(final DecryptedSignUpData decryptedSignUpData) {
        final List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.hasText(decryptedSignUpData.getLoginId())) {
            if (!LOGIN_ID_PATTERN.matcher(decryptedSignUpData.getLoginId()).matches()) {
                errors.add(ValidationError.of("loginId", "SignUpRequest.loginId pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("loginId", "SignUpRequest.loginId must not be null."));
        }
        if (StringUtils.hasText(decryptedSignUpData.getPassword())) {
            if (!PASSWORD_ID_PATTERN.matcher(decryptedSignUpData.getPassword()).matches()) {
                errors.add(ValidationError.of("password", "SignUpRequest.password pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("password", "SignUpRequest.password must not be null."));
        }
        if (StringUtils.hasText(decryptedSignUpData.getNickname())) {
            if (!NICKNAME_ID_PATTERN.matcher(decryptedSignUpData.getNickname()).matches()) {
                errors.add(ValidationError.of("nickname", "SignUpRequest.nickname pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("nickname", "SignUpRequest.nickname must not be null."));
        }

        return errors;
    }
}
