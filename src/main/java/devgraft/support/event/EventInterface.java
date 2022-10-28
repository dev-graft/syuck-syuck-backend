package devgraft.support.event;

import java.time.LocalDateTime;

public interface EventInterface {
    String getTag();
    LocalDateTime getTimestamp();
}
