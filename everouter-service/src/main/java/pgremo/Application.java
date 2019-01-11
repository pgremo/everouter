package pgremo;

import org.glassfish.jersey.server.ResourceConfig;
import pgremo.logging.LoggingConfigurator;

import static java.lang.System.setProperty;
import static javax.ws.rs.core.UriBuilder.fromUri;
import static org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer;

public class Application {
    static {
        setProperty("java.util.logging.config.class", LoggingConfigurator.class.getName());
    }

    public static void main(String... args) {
        createHttpServer(
                fromUri("http://localhost/").port(8080).build(),
                new ResourceConfig().packages("pgremo")
        );
    }
}
