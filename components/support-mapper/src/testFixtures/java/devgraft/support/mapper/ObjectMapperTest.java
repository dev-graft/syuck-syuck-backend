package devgraft.support.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperTest {
    private final ObjectMapper objectMapper;
    protected ObjectMapperTest() {
        objectMapper = new ObjectMapperConfig().objectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
