package pgremo;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface Property {
    String NULL = "pgremo.Property:NULL";

    @Nonbinding String value() default "";

    String pattern() default "";

    String defaultValue() default NULL;
}
