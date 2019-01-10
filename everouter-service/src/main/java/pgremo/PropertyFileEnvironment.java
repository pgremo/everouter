package pgremo;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.ofNullable;

public class PropertyFileEnvironment implements Environment {
    private final Properties properties;

    public PropertyFileEnvironment(URL file, boolean required) throws IOException {
        properties = new Properties();
        try (InputStream stream = file.openStream()) {
            properties.load(stream);
        } catch (IOException e) {
            if (required) throw e;
        }
    }

    @Override
    public Optional<String> get(String key) {
        return ofNullable(properties.getProperty(key));
    }
}
