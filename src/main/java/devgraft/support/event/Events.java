package devgraft.support.event;

import org.springframework.context.ApplicationEventPublisher;


public class Events {
    private static ApplicationEventPublisher publisher;

    protected static void setPublisher(final ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static <T extends Event> void raise(T event) {
        if (null != publisher) publisher.publishEvent(event);
    }
}
