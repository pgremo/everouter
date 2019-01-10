package pgremo;

import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;

public class ChainingEnvironment implements Environment {
    private Environment[] chain;

    public ChainingEnvironment(Environment... chain) {
        this.chain = chain;
    }

    @Override
    public Optional<String> get(String key) {
        EnvironmentNames keys = new EnvironmentNames(key);
        return stream(chain)
                .flatMap(x -> keys.stream().map(x::get))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(empty());
    }
}
