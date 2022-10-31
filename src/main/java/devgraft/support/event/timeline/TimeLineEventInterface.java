package devgraft.support.event.timeline;

import devgraft.support.event.EventInterface;

public interface TimeLineEventInterface extends EventInterface {
    String getMemberId();
    String getContent();
}