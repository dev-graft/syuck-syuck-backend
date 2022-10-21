package devgraft.chat.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StatusController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/status")
    public void enter(final Message message) {
        log.info("memberId: {} message: {}", message.getMemberId(), message.getValue());
        template.convertAndSend("/sub/enter/" + message.getMemberId(), message);
    }

    @NoArgsConstructor
    @Getter
    public static class Message {
        private String memberId;
        private String value;
    }
}
