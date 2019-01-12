package pgremo.environment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import static pgremo.environment.EnvironmentHolder.getEnvironment;

public class EnvironmentExtension implements Extension {
    public void addEnvironment(@Observes AfterBeanDiscovery event) {
        event.addBean()
                .types(Environment.class)
                .scope(ApplicationScoped.class)
                .addQualifier(Default.Literal.INSTANCE)
                .produceWith(x -> getEnvironment());
    }
}
