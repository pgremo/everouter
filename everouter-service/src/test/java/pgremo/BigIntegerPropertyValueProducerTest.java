package pgremo;

import org.jboss.weld.junit5.auto.*;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoWeld
@AddPackages(Application.class)
@AddBeanClasses(TestEnvironment.class)
@AddExtensions(CDIExtension.class)
@EnableAlternatives(TestEnvironment.class)
class BigIntegerPropertyValueProducerTest {

    @Inject
    @Property("bigInteger")
    BigInteger value;

    @Test
    public void shouldSetValue() {
        assertThat(value).isEqualTo(3456);
    }

    @Inject
    @Property("nullBigInteger")
    BigInteger nullValue;

    @Test
    public void shouldSetNull(){
        assertThat(nullValue).isNull();
    }
}

@Alternative
class TestEnvironment implements Environment {
    private Map<String, String> values = Map.of("bigInteger", "3456");

    @Override
    public Optional<String> get(String key) {
        return ofNullable(values.get(key));
    }
}