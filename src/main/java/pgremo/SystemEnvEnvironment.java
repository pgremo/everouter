package pgremo;


import java.util.Optional;

import static java.lang.System.getenv;

public class SystemEnvEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(getenv(key));
    }
}
