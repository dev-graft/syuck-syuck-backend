package devgraft.support.event.push;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import devgraft.support.event.push.PushEventInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PushEventHandler {
    private final ObjectMapper objectMapper;

    @EventListener(PushEventInterface.class)
    public void handle(final PushEventInterface event) throws JsonProcessingException {
        log.info("PushEvent: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event));
    }
}
