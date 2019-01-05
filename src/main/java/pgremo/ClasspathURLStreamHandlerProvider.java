package pgremo;

import java.net.URLStreamHandler;
import java.net.spi.URLStreamHandlerProvider;

public class ClasspathURLStreamHandlerProvider extends URLStreamHandlerProvider {
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return "classpath".equals(protocol) ? new ClasspathURLStreamHandler() : null;
    }
}
