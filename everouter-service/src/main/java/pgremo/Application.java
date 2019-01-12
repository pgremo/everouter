package pgremo;

import org.glassfish.jersey.server.ResourceConfig;
import pgremo.environment.ChainingEnvironment;
import pgremo.environment.PropertyFileEnvironment;
import pgremo.environment.SystemEnvEnvironment;
import pgremo.environment.SystemPropertiesEnvironment;
import pgremo.logging.LoggingConfigurator;

import java.io.IOException;
import java.net.URL;

import static java.lang.System.setProperty;
import static javax.ws.rs.core.UriBuilder.fromUri;
import static org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer;
import static pgremo.environment.EnvironmentHolder.setEnvironment;

public class Application {
    static {
        setProperty("java.util.logging.config.class", LoggingConfigurator.class.getName());

        try {
            setEnvironment(new ChainingEnvironment(
                    new SystemEnvEnvironment(),
                    new SystemPropertiesEnvironment(),
                    new PropertyFileEnvironment(new URL("classpath:/application.properties"), false)
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        createHttpServer(
                fromUri("http://localhost/").port(8080).build(),
                new ResourceConfig().packages("pgremo")
        );
    }
}
