package pgremo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.*;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class CDIExtension implements Extension {
    private Set<String> profiles;
    private Environment environment;

    public void configureEnvironment(@Observes BeforeBeanDiscovery event) throws IOException {
        environment = new ChainingEnvironment(
                new SystemEnvEnvironment(),
                new SystemPropertiesEnvironment(),
                new PropertyFileEnvironment(new URL("classpath:/application.properties"), false)
        );
        profiles = environment.get("everouter.profiles")
                .map(x -> x.split("\\s*,\\s*"))
                .map(x -> Stream.of(x).collect(toSet()))
                .orElse(emptySet());
    }

    public void addEnvironment(@Observes AfterBeanDiscovery event) {
        event.addBean()
                .types(Environment.class)
                .scope(ApplicationScoped.class)
                .addQualifier(Default.Literal.INSTANCE)
                .produceWith(x -> environment);
    }

    public void checkProfiles(@Observes @WithAnnotations(Profile.class) ProcessAnnotatedType<?> event) {
        Set<Profile> annotations = event.getAnnotatedType().getAnnotations(Profile.class);
        if (annotations.isEmpty()) return;
        annotations.stream()
                .flatMap(x -> Stream.of(x.value()))
                .distinct()
                .filter(profiles::contains)
                .findFirst()
                .ifPresentOrElse(x -> {
                }, event::veto);
    }
}
