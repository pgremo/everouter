package pgremo;

import javax.enterprise.inject.se.SeContainer;

import static java.lang.System.setProperty;
import static javax.enterprise.inject.se.SeContainerInitializer.newInstance;

public class Application {
    static {
        setProperty("java.util.logging.config.class", LoggingConfigurator.class.getName());
        setProperty("org.jboss.logging.provider", "slf4j");
    }

    public static void main(String... args) {
        try (SeContainer container = newInstance().initialize()) {
            container.select(Runner.class).get().run(args);
        }
    }
}
