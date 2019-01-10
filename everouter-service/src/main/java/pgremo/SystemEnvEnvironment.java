package pgremo;


import java.util.Optional;

import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

public class SystemEnvEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return ofNullable(getenv(key));
    }
}
