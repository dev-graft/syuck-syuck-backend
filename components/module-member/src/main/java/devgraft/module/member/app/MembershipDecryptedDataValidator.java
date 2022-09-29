package devgraft.module.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static devgraft.module.member.domain.MemberPatterns.LOGIN_ID_PATTERN;
import static devgraft.module.member.domain.MemberPatterns.NICKNAME_ID_PATTERN;
import static devgraft.module.member.domain.MemberPatterns.PASSWORD_ID_PATTERN;

@Component
public class MembershipDecryptedDataValidator {

    public List<ValidationError> validate(final MembershipDecryptedData decryptedData) {
        final List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.hasText(decryptedData.getLoginId())) {
            if (!LOGIN_ID_PATTERN.matcher(decryptedData.getLoginId()).matches()) {
                errors.add(ValidationError.of("loginId", "MembershipRequest.loginId pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("loginId", "MembershipRequest.loginId must not be null."));
        }
        if (StringUtils.hasText(decryptedData.getPassword())) {
            if (!PASSWORD_ID_PATTERN.matcher(decryptedData.getPassword()).matches()) {
                errors.add(ValidationError.of("password", "MembershipRequest.password pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("password", "MembershipRequest.password must not be null."));
        }
        if (StringUtils.hasText(decryptedData.getNickname())) {
            if (!NICKNAME_ID_PATTERN.matcher(decryptedData.getNickname()).matches()) {
                errors.add(ValidationError.of("nickname", "MembershipRequest.nickname pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname must not be null."));
        }

        return errors;
    }
}
