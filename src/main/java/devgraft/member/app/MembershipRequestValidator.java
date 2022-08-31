package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static devgraft.member.app.MemberPatterns.LOGIN_ID_PATTERN;
import static devgraft.member.app.MemberPatterns.NICKNAME_ID_PATTERN;
import static devgraft.member.app.MemberPatterns.PASSWORD_ID_PATTERN;

@Component
public class MembershipRequestValidator {

    public List<ValidationError> validate(final MembershipRequest request) {
        final List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.hasText(request.getLoginId())) {
            if (!LOGIN_ID_PATTERN.matcher(request.getLoginId()).matches()) {
                errors.add(ValidationError.of("loginId", "MembershipRequest.loginId pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("loginId", "MembershipRequest.loginId must not be null."));
        }
        if (StringUtils.hasText(request.getPassword())) {
            if (!PASSWORD_ID_PATTERN.matcher(request.getPassword()).matches()) {
                errors.add(ValidationError.of("password", "MembershipRequest.password pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("password", "MembershipRequest.password must not be null."));
        }
        if (StringUtils.hasText(request.getNickname())) {
            if (!NICKNAME_ID_PATTERN.matcher(request.getNickname()).matches()) {
                errors.add(ValidationError.of("nickname", "MembershipRequest.nickname pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname must not be null."));
        }

        return errors;
    }
}
