package com.epam.spring.core.logger;

import com.epam.spring.core.bean.Event;
import org.springframework.stereotype.Component;

@Component
public interface EventLogger {
    void logEvent(Event event);
}
