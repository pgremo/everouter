package pgremo.environment;

import java.util.Optional;

public interface Environment {

    Optional<String> get(String key);

}
