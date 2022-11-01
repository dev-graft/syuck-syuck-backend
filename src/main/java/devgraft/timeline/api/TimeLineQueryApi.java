package devgraft.timeline.api;

import devgraft.common.SearchResult;
import devgraft.timeline.query.TimeLineData;
import devgraft.timeline.query.TimeLineDataDao;
import devgraft.timeline.query.TimeLineDataSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.TIMELINE_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class TimeLineQueryApi {
    private final TimeLineDataDao timeLineDataDao;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + TIMELINE_URL_PREFIX)
    public SearchResult<TimeLineDetail> searchTimeLineList(@RequestParam(name = "target") final String memberId, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<TimeLineData> timeLineData = timeLineDataDao.findAll(TimeLineDataSpec.memberIdEquals(memberId).and(TimeLineDataSpec.orderByDESC()), PageRequest.of(page, 20));

        final List<TimeLineDetail> values = timeLineData.stream()
                .map(TimeLineDetail::mapped)
                .collect(Collectors.toList());

        return SearchResult.<TimeLineDetail>builder()
                .totalPage(timeLineData.getTotalPages())
                .totalElements(timeLineData.getTotalElements())
                .page(page)
                .size(values.size())
                .values(values)
                .build();
    }

    @Builder
    @Getter
    public static class TimeLineDetail {
        private final Long timeLineId;
        private final String tag;
        private final String code;
        private final String memberId;
        private final String content;
        private final LocalDateTime issuedAt;

        public static TimeLineDetail mapped(final TimeLineData timeLineData) {
            return builder()
                    .timeLineId(timeLineData.getId())
                    .tag(timeLineData.getTag())
                    .code(timeLineData.getCode())
                    .memberId(timeLineData.getMemberId())
                    .content(timeLineData.getContent())
                    .issuedAt(timeLineData.getCreatedAt())
                    .build();
        }
    }
}
