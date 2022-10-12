package devgraft.common.wrap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProcessResult {
    private String message;
    private boolean success;
}
