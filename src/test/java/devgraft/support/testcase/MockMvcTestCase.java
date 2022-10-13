package devgraft.support.testcase;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public abstract class MockMvcTestCase extends ObjectMapperTestCase {
    protected MockMvc mockMvc;
    protected abstract AbstractMockMvcBuilder<?> getMockMvcBuilder();

    protected void beforeEach() {}

    @BeforeEach
    void setUp() {
        beforeEach();
        mockMvc = getMockMvcBuilder()
                .alwaysDo(print())
                .build();
    }
}
