package devgraft.local;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class InitLogApp {
    @Value("${version}")
    private String version;
    private final static String BLOCK = "=";
    private final static int BLOCK_SIZE = 50;
    @PostConstruct
    public void init() {
        PrettyLogger.builder()
                .add("Team: " + "DevGraft")
                .add("VERSION: " + version)
                .add("Last UpdateAt: " + "2022-09-11")
                .build()
                .show();
    }

    private void prettyBlockLog() {
        log.info(BLOCK.repeat(BLOCK_SIZE));
    }

    private void prettyLog(final String msg) {
        int size = BLOCK_SIZE % 2 == 0 ? 1 : 2;
        int msgLength = msg.length();


        // BLOCK_SIZE 홀수냐 짝수냐에 따라 1,2개의 외곽 블록
        // BLOCK_SIZE 홀수냐 짝수냐에 따라 끝에 스페이스바 1~ 2
    }
}
