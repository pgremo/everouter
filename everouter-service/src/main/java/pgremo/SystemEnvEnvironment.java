package pgremo;


import javax.enterprise.inject.Alternative;
import java.util.Optional;

import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

@Alternative
public class SystemEnvEnvironment implements Environment {
    @Override
    public Optional<String> get(String key) {
        return ofNullable(getenv(key));
    }
}
