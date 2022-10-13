package devgraft.support.testcase;

import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

public abstract class StandaloneMockMvcTestCase extends MockMvcTestCase {
    private static final HandlerMethodArgumentResolver[] CASE = new HandlerMethodArgumentResolver[0];
    protected abstract StandaloneMockMvcBuilder getStandaloneMockMvcBuilder();

    protected List<HandlerMethodArgumentResolver> getCustomArgumentResolvers() {
        return new ArrayList<>();
    }

    @Override
    protected AbstractMockMvcBuilder<?> getMockMvcBuilder() {
        return getStandaloneMockMvcBuilder()
                .setCustomArgumentResolvers(getCustomArgumentResolvers().toArray(CASE));
    }
}
