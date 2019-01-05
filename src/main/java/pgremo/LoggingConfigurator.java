package pgremo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.util.logging.LogManager.getLogManager;

public class LoggingConfigurator {
    public LoggingConfigurator() throws IOException {
        try (InputStream stream = new URL("classpath:/logging.properties").openStream()) {
            getLogManager().readConfiguration(stream);
        }
    }
}
