package pgremo;

import javax.enterprise.inject.se.SeContainer;

import static javax.enterprise.inject.se.SeContainerInitializer.newInstance;

public class Application {
    static {
        System.setProperty("java.util.logging.config.class", LoggingConfigurator.class.getName());
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    public static void main(String... args) {
        try (SeContainer container = newInstance().initialize()) {
            container.select(Runner.class).get().run(args);
        }
    }
}
