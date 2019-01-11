package pgremo.environment;

import pgremo.environment.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import java.io.IOException;
import java.net.URL;

import static pgremo.environment.EnvironmentHolder.getEnvironment;
import static pgremo.environment.EnvironmentHolder.setEnvironment;

public class EnvironmentExtension implements Extension {

    public EnvironmentExtension() throws IOException {
        setEnvironment(new ChainingEnvironment(
                new SystemEnvEnvironment(),
                new SystemPropertiesEnvironment(),
                new PropertyFileEnvironment(new URL("classpath:/application.properties"), false)
        ));
    }

    public void addEnvironment(@Observes AfterBeanDiscovery event) {
        event.addBean()
                .types(Environment.class)
                .scope(ApplicationScoped.class)
                .addQualifier(Default.Literal.INSTANCE)
                .produceWith(x -> getEnvironment());
    }
}
