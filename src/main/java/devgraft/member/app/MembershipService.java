package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final MembershipRequestValidator membershipRequestValidator;

    public void membership(MembershipRequest request) {
        List<ValidationError> errors = membershipRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors);


    }
}
