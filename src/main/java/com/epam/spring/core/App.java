package com.epam.spring.core;


import com.epam.spring.core.bean.Client;
import com.epam.spring.core.bean.Event;
import com.epam.spring.core.bean.EventType;
import com.epam.spring.core.logger.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class App {
    private Client client;
    private EventLogger defaultEventLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger defaultEventLogger,
            //(Client client, @Qualifier("consoleEventLogger") EventLogger defaultEventLogger,
//               @Value("#{${loggerMap}}")
                Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultEventLogger = defaultEventLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event eventInfo = (Event)ctx.getBean("event");
        eventInfo.setMsg("Everything is ok!");
        Event eventError = (Event)ctx.getBean("event");
        eventError.setMsg("Error!!!");

        app.logEvent(EventType.INFO, eventInfo);
        app.logEvent(EventType.ERROR, eventError);
        ctx.close();
    }

    private void logEvent(EventType type, Event event) {
        EventLogger logger = loggers.get(type);

        if (logger == null) {
            logger = defaultEventLogger;
        }

        logger.logEvent(event);
    }
}
