package pgremo.profile;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static pgremo.environment.EnvironmentHolder.getEnvironment;

public class ProfileExtension implements Extension {
    private Set<String> profiles;

    public ProfileExtension() throws IOException {
        profiles = getEnvironment().get("everouter.profiles")
                .map(x -> x.split("\\s*,\\s*"))
                .map(x -> Stream.of(x).collect(toSet()))
                .orElse(emptySet());
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
