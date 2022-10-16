package devgraft.support.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import devgraft.support.mapper.ObjectMapperConfig;

public class ObjectMapperTestCase {
    private final ObjectMapper objectMapper;
    protected ObjectMapperTestCase() {
        objectMapper = new ObjectMapperConfig().objectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
