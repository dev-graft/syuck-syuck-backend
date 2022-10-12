package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static devgraft.member.domain.MemberPatterns.NICKNAME_ID_PATTERN;

@Component
public class UpdateMemberProfileValidator {
    public List<ValidationError> validate(final UpdateMemberProfileRequest request) {
        final List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.hasText(request.getNickname())) {
            if (!NICKNAME_ID_PATTERN.matcher(request.getNickname()).matches()) {
                errors.add(ValidationError.of("nickname", "nickname pattern don't match."));
            }
        } else {
            errors.add(ValidationError.of("nickname", "nickname must not be null."));
        }

        if (!StringUtils.hasText(request.getStateMessage())) {
            errors.add(ValidationError.of("stateMessage", "stateMessage must not be null."));

        }
        return errors;
    }
}
