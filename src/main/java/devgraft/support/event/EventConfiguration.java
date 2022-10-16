package devgraft.support.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EventConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean events() {
        return () -> Events.setPublisher(applicationContext);
    }
}
