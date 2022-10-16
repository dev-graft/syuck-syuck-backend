package devgraft.support.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Event {
    private final String tag;
    private final LocalDateTime localDateTime;

    public Event(final String tag) {
        this.tag = tag;
        this.localDateTime = LocalDateTime.now();
    }
}
