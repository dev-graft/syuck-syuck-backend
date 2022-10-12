package devgraft.support.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponentModule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperFactory {
    private static final ObjectMapper objectMapper = new ObjectMapper()
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JsonMapperJava8DateTimeModule())
            .registerModule(new JsonComponentModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
