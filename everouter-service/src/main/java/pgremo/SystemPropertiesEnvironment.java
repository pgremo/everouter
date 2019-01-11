package pgremo;

import javax.enterprise.inject.Alternative;
import java.util.Optional;

import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;

@Alternative
public class SystemPropertiesEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return ofNullable(getProperty(key));
    }
}
