package pgremo;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;

public class ObjectInstancePropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(ClassLoader.getSystemClassLoader().loadClass(text).getDeclaredConstructor().newInstance());
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
