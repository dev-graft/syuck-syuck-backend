package devgraft.module.member.api;

import devgraft.module.member.app.ProfileImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExtraApi {

    @GetMapping("api/extra/dp-image")
    public String getDefaultProfileImage() {
        return ProfileImageProvider.create();
    }
}
