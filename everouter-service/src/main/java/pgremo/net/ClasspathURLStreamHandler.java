package pgremo.net;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;

public class ClasspathURLStreamHandler extends URLStreamHandler {
    private final ClassLoader classLoader;

    public ClasspathURLStreamHandler() {
        classLoader = Stream.<Supplier<ClassLoader>>of(
                () -> currentThread().getContextClassLoader(),
                ClasspathURLStreamHandler.class::getClassLoader,
                ClassLoader::getSystemClassLoader
        )
                .map(x -> {
                    try {
                        return x.get();
                    } catch (SecurityException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        URL url = classLoader.getResource(u.getPath().substring(1));
        if (url != null) return url.openConnection();
        throw new IOException(format("%s does not exist", u));
    }
}
