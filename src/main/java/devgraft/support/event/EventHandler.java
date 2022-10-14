package devgraft.support.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventHandler {
    @EventListener(Event.class)
    public void handle(final Event event) {
        log.info("Event: {}", event);
    }
}
