package devgraft.support.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Events {
    private static ApplicationEventPublisher publisher;

    protected static void setPublisher(final ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static <T extends EventInterface> void raise(T event) {
        if (null != publisher) publisher.publishEvent(event);
    }
}
