package pgremo;

import java.util.Optional;

import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;

public class SystemPropertiesEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return ofNullable(getProperty(key));
    }
}
