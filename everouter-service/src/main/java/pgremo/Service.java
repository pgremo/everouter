package pgremo;

import org.glassfish.jersey.server.ResourceConfig;

import static javax.ws.rs.core.UriBuilder.fromUri;
import static org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer;

public class Service implements Runner {
    @Override
    public void run(String... args) {
        createHttpServer(
                fromUri("http://localhost/").port(8080).build(),
                new ResourceConfig(MyResource.class)
        );
    }
}
