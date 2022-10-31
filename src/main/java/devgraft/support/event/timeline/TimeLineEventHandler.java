package devgraft.support.event.timeline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TimeLineEventHandler {
    private final ObjectMapper objectMapper;

    @EventListener(TimeLineEventInterface.class)
    public void handle(final TimeLineEventInterface event) throws JsonProcessingException {
        // 회원 조회
        // 타임라인 메세지는 수정되어선 안됨
        // 타임라인 테이블에 저장
        log.info("TimeLineEvent: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event));
    }
}
