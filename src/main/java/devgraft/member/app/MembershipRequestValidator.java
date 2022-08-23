package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;


// TODO 회원가입 과정과 프로필 수정과정에 아이디, 별명 정규식이 겹치므로 별도의 파일로 빼낼 예정입니다
// TODO Password는 Hash형태로 전달받게되므로, 정규식이 지켜졌는지 확인하기 어렵기에 해당 검증과정에서 제외합니다.

@RequiredArgsConstructor
@Component
public class MembershipRequestValidator {
    private static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^(?=.*[a-z])[a-z|A-Z0-9]{5,20}$");
    private static final Pattern PASSWORD_ID_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()/?~])[a-z|A-Z0-9!@#$%^&*()/?~]{8,25}$");
    private static final Pattern NICKNAME_ID_PATTERN = Pattern.compile("^[\\w|가-힣ㄱ-ㅎㅏ-ㅣ]{1,10}$");

    public List<ValidationError> validate(final MembershipRequest request) {
        final List<ValidationError> errors = new ArrayList<>();

        validatedRequiredElements(request, errors);
        validatedPattern(request, errors);

        return errors;
    }

    private void validatedPattern(final MembershipRequest request, final Collection<? super ValidationError> errors) {
        if (StringUtils.hasText(request.getLoginId()) && !LOGIN_ID_PATTERN.matcher(request.getLoginId()).matches()) {
            errors.add(ValidationError.of("loginId", "MembershipRequest.loginId pattern must match."));
        }
        if (StringUtils.hasText(request.getPassword()) && !PASSWORD_ID_PATTERN.matcher(request.getPassword()).matches()) {
            errors.add(ValidationError.of("password", "MembershipRequest.password pattern must match."));
        }
        if (StringUtils.hasText(request.getNickname()) && !NICKNAME_ID_PATTERN.matcher(request.getNickname()).matches()) {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname pattern must match."));
        }
    }

    private void validatedRequiredElements(final MembershipRequest request, final Collection<? super ValidationError> errors) {
        if (!StringUtils.hasText(request.getLoginId())) {
            errors.add(ValidationError.of("loginId", "MembershipRequest.loginId must not be null."));
        }
        if (!StringUtils.hasText(request.getPassword())) {
            errors.add(ValidationError.of("password", "MembershipRequest.password must not be null."));
        }
        if (!StringUtils.hasText(request.getNickname())) {
            errors.add(ValidationError.of("nickname", "MembershipRequest.nickname must not be null."));
        }
    }
}
