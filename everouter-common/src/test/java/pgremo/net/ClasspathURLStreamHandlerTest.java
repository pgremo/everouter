package pgremo.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


class ClasspathURLStreamHandlerTest {
    @Test
    public void shouldLoadProperties() throws IOException {
        URL url = new URL("classpath:/pgremo/test.properties");
        Properties properties = new Properties();
        properties.load(url.openStream());
        assertThat(properties.get("key")).isEqualTo("value");
    }
}