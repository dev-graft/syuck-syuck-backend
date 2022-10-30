package devgraft.support.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Event implements EventInterface {
    private final String tag;
    private final LocalDateTime timestamp;

    public Event(final String tag) {
        this.tag = tag;
        this.timestamp = LocalDateTime.now();
    }
}
