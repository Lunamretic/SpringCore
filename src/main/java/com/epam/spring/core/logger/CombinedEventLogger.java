package com.epam.spring.core.logger;

import com.epam.spring.core.bean.Event;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("combinedEventLogger")
public class CombinedEventLogger implements EventLogger {
    private Collection<EventLogger> loggers;

    CombinedEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        loggers.forEach(item -> item.logEvent(event));

    }
}
