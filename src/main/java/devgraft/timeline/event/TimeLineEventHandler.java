package devgraft.timeline.event;

import devgraft.timeline.domain.TimeLine;
import devgraft.timeline.domain.TimeLineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TimeLineEventHandler {
    private final TimeLineRepository timeLineRepository;

    @EventListener(TimeLineEventInterface.class)
    @Async
    public void handle(final TimeLineEventInterface event) {
        final TimeLine timeLine = TimeLine.builder()
                .tag(event.getTag())
                .code(event.getCode())
                .memberId(event.getMemberId())
                .content(event.getContent())
                .build();
        timeLineRepository.save(timeLine);
    }
}
