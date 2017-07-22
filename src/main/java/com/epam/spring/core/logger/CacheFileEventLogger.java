package com.epam.spring.core.logger;

import com.epam.spring.core.bean.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:project.properties")
public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    private CacheFileEventLogger(@Value("${filename}") String filename, @Value("${cacheSize}") int cacheSize) {
        super(filename);
        this.cacheSize = cacheSize;
        cache = new ArrayList<>();
    }

    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        cache.forEach(str -> {
            try {
                FileUtils.writeStringToFile(getFile(), str.getMsg(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @PreDestroy
    private void destroy() {
        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }
}
