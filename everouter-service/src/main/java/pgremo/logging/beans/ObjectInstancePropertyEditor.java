package pgremo.logging.beans;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;

import static java.lang.ClassLoader.getSystemClassLoader;

public class ObjectInstancePropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(getSystemClassLoader().loadClass(text).getDeclaredConstructor().newInstance());
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
