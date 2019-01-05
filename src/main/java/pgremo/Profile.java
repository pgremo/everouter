package pgremo;

import javax.enterprise.util.Nonbinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({
        TYPE,
        METHOD,
        FIELD,
        PARAMETER
})
public @interface Profile {
    @Nonbinding String[] value();
}
