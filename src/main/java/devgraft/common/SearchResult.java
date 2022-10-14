package devgraft.common;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchResult<T> {
    private final int totalPage;    // 페이지
    private final long totalElements; // 최대 크기
    private final int page;    // 페이지
    private final int size;    // 크기
    private final List<T> values;
}
