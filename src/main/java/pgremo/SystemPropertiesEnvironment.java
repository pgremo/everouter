package pgremo;

import java.util.Optional;

import static java.lang.System.getProperty;

public class SystemPropertiesEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(getProperty(key));
    }
}
