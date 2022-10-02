package devgraft.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static devgraft.support.mapper.ObjectMapperFactory.getObjectMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonLogger {

    public static void logI(Logger logger, String format, Object... objs) {
        try {
            final ObjectWriter objectWriter = getObjectMapper().writerWithDefaultPrettyPrinter();
            List<String> strings = new ArrayList<>();
            for (Object obj : objs) {
                strings.add(objectWriter.writeValueAsString(obj));
            }
            logger.info(format, strings.toArray());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
