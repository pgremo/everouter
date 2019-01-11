package pgremo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static java.beans.PropertyEditorManager.findEditor;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.function.Predicate.not;

@ApplicationScoped
public class PropertyValueProducer {

    private Environment environment;

    @Inject
    public PropertyValueProducer(Environment environment) {
        this.environment = environment;
    }

    @Produces
    @Dependent
    @Property
    public String produceStringProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public Boolean produceBooleanProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public Integer produceIntegerProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public Long produceLongProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public Float produceFloatProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public Double produceDoubleProperty(InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Produces
    @Dependent
    @Property
    public BigInteger produceBigIntegerProperty(InjectionPoint injectionPoint) {
        Optional<Property> annotation = getAnnotation(injectionPoint);
        return getProperty(annotation)
                .map(x -> {
                    try {
                        DecimalFormat format = annotation
                                .map(Property::pattern)
                                .filter(not(String::isEmpty))
                                .map(DecimalFormat::new)
                                .orElseGet(DecimalFormat::new);
                        format.setParseBigDecimal(true);
                        return (BigDecimal) format.parseObject(x);
                    } catch (ParseException e) {
                        throw new InjectionException(e);
                    }
                })
                .map(BigDecimal::toBigInteger)
                .orElse(null);
    }

    @Produces
    @Dependent
    @Property
    public BigDecimal produceBigDecimalProperty(InjectionPoint injectionPoint) {
        Optional<Property> annotation = getAnnotation(injectionPoint);
        return getProperty(annotation)
                .map(x -> {
                    try {
                        DecimalFormat format = annotation
                                .map(Property::pattern)
                                .filter(not(String::isEmpty))
                                .map(DecimalFormat::new)
                                .orElseGet(DecimalFormat::new);
                        format.setParseBigDecimal(true);
                        return (BigDecimal) format.parseObject(x);
                    } catch (ParseException e) {
                        throw new InjectionException(e);
                    }
                })
                .orElse(null);
    }

    @Produces
    @Dependent
    @Property
    public Date produceDateProperty(InjectionPoint injectionPoint) {
        Optional<Property> annotation = getAnnotation(injectionPoint);
        return getProperty(annotation)
                .map(x -> {
                    try {
                        return (Date) annotation
                                .map(Property::pattern)
                                .filter(not(String::isEmpty))
                                .map(SimpleDateFormat::new)
                                .map(Format.class::cast)
                                .orElseGet(ISO_DATE_TIME::toFormat)
                                .parseObject(x);
                    } catch (ParseException e) {
                        throw new InjectionException(e);
                    }
                })
                .orElse(null);
    }

    private <T> T getValue(InjectionPoint injectionPoint) {
        Optional<Property> annotation = getAnnotation(injectionPoint);
        return getProperty(annotation)
                .map(x -> {
                    PropertyEditor editor = findEditor((Class<T>) injectionPoint.getType());
                    editor.setAsText(x);
                    return (T) editor.getValue();
                })
                .orElse(null);
    }

    private Optional<String> getProperty(Optional<Property> annotation) {
        return annotation
                .map(x -> environment.get(x.value())
                        .orElseGet(() -> Property.NULL.equals(x.defaultValue()) ? null : x.defaultValue()));
    }

    private Optional<Property> getAnnotation(InjectionPoint point) {
        return Optional.ofNullable(point.getAnnotated().getAnnotation(Property.class));
    }
}