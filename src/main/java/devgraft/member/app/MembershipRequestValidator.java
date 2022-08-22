package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class MembershipRequestValidator {
    private static final Pattern MEMBER_ID_PATTERN = Pattern.compile("^(?=.*[a-z])[a-z|A-Z0-9]{5,20}$");
    private static final Pattern PASSWORD_ID_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()/?~])[a-z|A-Z0-9!@#$%^&*()/?~]{8,25}$");
    private static final Pattern NICKNAME_ID_PATTERN = Pattern.compile("^[\\w|가-힣ㄱ-ㅎㅏ-ㅣ]{1,10}$");


    public List<ValidationError> validate(final MembershipRequest request) {
        final List<ValidationError> errors = new ArrayList<>();

        validatedRequiredElements(request, errors);
        validatedPattern(request, errors);

        return errors;
    }

    private void validatedPattern(final MembershipRequest request, final List<ValidationError> errors) {
        if (StringUtils.hasText(request.getMemberId()) && !MEMBER_ID_PATTERN.matcher(request.getMemberId()).matches()) {
            errors.add(ValidationError.of("memberId", "MembershipRequest.memberId pattern must match."));
        }

        if (StringUtils.hasText(request.getPassword()) && !PASSWORD_ID_PATTERN.matcher(request.getPassword()).matches()) {
            errors.add(ValidationError.of("password", "MembershipRequest.password pattern must match."));
        }

        if (StringUtils.hasText(request.getNickname()) && !NICKNAME_ID_PATTERN.matcher(request.getNickname()).matches()) {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname pattern must match."));
        }
    }

    private void validatedRequiredElements(final MembershipRequest request, final List<ValidationError> errors) {
        if (!StringUtils.hasText(request.getMemberId())) {
            errors.add(ValidationError.of("memberId", "MembershipRequest.memberId must not be null."));
        }
        if (!StringUtils.hasText(request.getPassword())) {
            errors.add(ValidationError.of("password", "MembershipRequest.password must not be null."));
        }
        if (!StringUtils.hasText(request.getNickname())) {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname must not be null."));
        }
    }
}
