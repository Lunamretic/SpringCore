package com.epam.spring.core.logger;

import com.epam.spring.core.bean.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
@PropertySource("classpath:client.properties")
public class FileEventLogger implements EventLogger {
    private File file;
    private String filename;

    public FileEventLogger(@Value("${filename}") String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        file.canWrite();
    }

    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
