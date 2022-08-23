package devgraft.member.api;

import devgraft.member.app.MemberIds;
import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MembershipService membershipService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MemberIds membership(@RequestBody final MembershipRequest request) {
        return membershipService.membership(request);
    }
}
