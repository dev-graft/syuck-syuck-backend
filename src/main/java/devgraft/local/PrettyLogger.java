package devgraft.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PrettyLogger {
    private final static String BLOCK = "=";
    private final static int BLOCK_MIN_SIZE = 50;
    private final String headerMessage;
    private final int BLOCK_SIZE;
    private final List<String> msgs;
    private static final Logger log = LoggerFactory.getLogger(PrettyLogger.class);;

    public PrettyLogger(final List<String> msgs, final String headerMessage) {
        this.msgs = msgs;
        this.headerMessage = headerMessage;
        BLOCK_SIZE = calSize();
    }

    public void show() {
        prettyHeaderLog();
        for (String msg : msgs) {
            prettyLog(msg);
        }
        log.info(BLOCK.repeat(BLOCK_SIZE));
    }

    private void prettyLog(final String msg) {
        final StringBuilder builder = new StringBuilder();
        int _size = BLOCK_SIZE - msg.length() - 4;
        builder.append(BLOCK.repeat(1));
        builder.append(" ".repeat(1));
        builder.append(msg);
        builder.append(" ".repeat(_size));
        builder.append(" ".repeat(1));
        builder.append(BLOCK.repeat(1));
        log.info(builder.toString());
    }

    private void prettyHeaderLog() {
        log.info(BLOCK.repeat((BLOCK_SIZE - headerMessage.length()) / 2 )
                + headerMessage +
                BLOCK.repeat((BLOCK_SIZE - headerMessage.length()) % 2 == 0 ? (BLOCK_SIZE - headerMessage.length()) / 2 : (BLOCK_SIZE - headerMessage.length() + 1) / 2));
    }

    private int calSize() {
        int size = BLOCK_MIN_SIZE;
        for (String msg : msgs) {
            size = Math.max(size, msg.length() + 10);
        }
        return size % 2 == 0 ? size : size + 1;
    }

    public static PrettyLoggerBuilder builder() {
        return new PrettyLoggerBuilder();
    }

    public static class PrettyLoggerBuilder {
        private final List<String> msgList = new ArrayList<>();;
        private String headerMessage = " Syuck Syuck Service ";

        public PrettyLoggerBuilder add(final String msg) {
            this.msgList.add(msg);
            return this;
        }

        public PrettyLoggerBuilder header(final String header) {
            this.headerMessage = header;
            return this;
        }

        public PrettyLogger build() {
            return new PrettyLogger(msgList, headerMessage);
        }
    }
}
