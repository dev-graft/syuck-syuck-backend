package devgraft.support.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventHandler {
    private final ObjectMapper objectMapper;

//    @EventListener(EventInterface.class)
    public void handle(final EventInterface event) throws JsonProcessingException {
        log.info("Event: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event));
    }
}
