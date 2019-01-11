package pgremo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
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

import static java.beans.PropertyEditorManager.findEditor;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

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
    public BigInteger produceBigIntegerProperty(InjectionPoint injectionPoint) throws ParseException {
        Property annotation = injectionPoint.getAnnotated().getAnnotation(Property.class);
        String value = getProperty(annotation);
        if (value == null) return null;
        String pattern = annotation.pattern();
        DecimalFormat format = pattern.isEmpty() ? new DecimalFormat() : new DecimalFormat(pattern);
        format.setParseBigDecimal(true);
        return ((BigDecimal) format.parseObject(value)).toBigInteger();
    }

    @Produces
    @Dependent
    @Property
    public BigDecimal produceBigDecimalProperty(InjectionPoint injectionPoint) throws ParseException {
        Property annotation = injectionPoint.getAnnotated().getAnnotation(Property.class);
        String value = getProperty(annotation);
        if (value == null) return null;
        String pattern = annotation.pattern();
        DecimalFormat format = pattern.isEmpty() ? new DecimalFormat() : new DecimalFormat(pattern);
        format.setParseBigDecimal(true);
        return (BigDecimal) format.parseObject(value);
    }

    @Produces
    @Dependent
    @Property
    public Date produceDateProperty(InjectionPoint injectionPoint) throws ParseException {
        Property annotation = injectionPoint.getAnnotated().getAnnotation(Property.class);
        String value = getProperty(annotation);
        if (value == null) return null;
        String pattern = annotation.pattern();
        Format format = pattern.isEmpty() ? ISO_DATE_TIME.toFormat() : new SimpleDateFormat(pattern);
        return (Date) format.parseObject(value);
    }

    private <T> T getValue(InjectionPoint injectionPoint) {
        Property annotation = injectionPoint.getAnnotated().getAnnotation(Property.class);
        String value = getProperty(annotation);
        if (value == null) return null;
        PropertyEditor editor = findEditor((Class<T>) injectionPoint.getType());
        editor.setAsText(value);
        return (T) editor.getValue();
    }

    private String getProperty(Property annotation) {
        return environment.get(annotation.value())
                .orElseGet(() -> Property.NULL.equals(annotation.defaultValue()) ? null : annotation.defaultValue());
    }
}