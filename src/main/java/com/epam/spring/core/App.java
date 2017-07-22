package com.epam.spring.core;


import com.epam.spring.core.bean.Client;
import com.epam.spring.core.bean.Event;
import com.epam.spring.core.bean.EventType;
import com.epam.spring.core.logger.EventLogger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("app")
public class App {
    private Client client;
    private EventLogger defaultEventLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, @Qualifier("consoleEventLogger") EventLogger defaultEventLogger,
               @Value("#{${loggers}}") Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultEventLogger = defaultEventLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.scan("com.epam.spring.core");
        context.refresh();
        App app = (App) context.getBean("app");

        Event eventInfo = (Event)context.getBean("event");
        eventInfo.setMsg("Everything is ok!");
        Event eventError = (Event)context.getBean("event");
        eventError.setMsg("Error!!!");

        app.logEvent(EventType.INFO, eventInfo);
        app.logEvent(EventType.ERROR, eventError);
        context.close();
    }

    private void logEvent(EventType type, Event event) {
        EventLogger logger = loggers.get(type);

        if (logger == null) {
            logger = defaultEventLogger;
        }

        logger.logEvent(event);
    }
}
