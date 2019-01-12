package pgremo.environment;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static pgremo.environment.Strings.capitalize;
import static pgremo.environment.Strings.uncapitalize;

public class EnvironmentNames implements Iterable<String> {

    private Set<String> values;

    public EnvironmentNames(String name) {
        String first = name == null ? "" : name;
        values = transformations.stream()
                .map(x -> x.apply(first))
                .collect(toCollection(LinkedHashSet::new));
    }

    public Stream<String> stream() {
        return values.stream();
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }


    private static final List<Function<String, String>> transformations;

    static {
        List<Function<String, String>> variations = asList(
                identity(),
                String::toLowerCase,
                String::toUpperCase
        );

        List<Function<String, String>> modifications = asList(
                identity(),
                x -> x.replace("-", "_"),
                x -> x.replace("_", "."),
                x -> x.replace(".", "_"),
                x -> Pattern
                        .compile("([^A-Z-])([A-Z])")
                        .matcher(x)
                        .replaceAll(r -> format("%s_%s", r.group(1), uncapitalize(r.group(2)))),
                x -> Pattern
                        .compile("([^A-Z-])([A-Z])")
                        .matcher(x)
                        .replaceAll(r -> format("%s-%s", r.group(1), uncapitalize(r.group(2)))),
                x -> Stream.of(x.split("[_\\-.]"))
                        .collect(new CamelCaseCollector(x)),
                x -> Stream.of(x.split("[_\\-.]"))
                        .map(String::toLowerCase)
                        .collect(new CamelCaseCollector(x))
        );

        transformations = variations.stream()
                .flatMap(x -> modifications.stream().map(x::compose))
                .collect(toList());
    }

    static class CamelCaseCollector implements Collector<String, StringBuilder, String> {

        private String value;

        CamelCaseCollector(String value) {
            this.value = value;
        }

        @Override
        public Supplier<StringBuilder> supplier() {
            return StringBuilder::new;
        }

        @Override
        public BiConsumer<StringBuilder, String> accumulator() {
            return (seed, x) -> seed.append(seed.length() == 0 ? x : capitalize(x));
        }

        @Override
        public BinaryOperator<StringBuilder> combiner() {
            return StringBuilder::append;
        }

        @Override
        public Function<StringBuilder, String> finisher() {
            return x -> {
                Stream.of("_", "-", ".")
                        .filter(value::endsWith)
                        .findFirst()
                        .ifPresent(x::append);
                return x.toString();
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return emptySet();
        }
    }
}
