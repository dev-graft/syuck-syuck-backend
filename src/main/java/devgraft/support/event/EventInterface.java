package devgraft.support.event;

import java.time.LocalDateTime;

public interface EventInterface {
    EventCode getEventCode();

    default String getTag() {
        return getEventCode().getTag();
    }

    default String getCode() {
        return getEventCode().getCode();
    }

    default LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }
}
