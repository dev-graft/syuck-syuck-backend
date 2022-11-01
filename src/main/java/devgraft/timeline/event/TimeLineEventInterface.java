package devgraft.timeline.event;

import devgraft.support.event.EventInterface;

public interface TimeLineEventInterface extends EventInterface {
    String getMemberId();
    String getContent();
}