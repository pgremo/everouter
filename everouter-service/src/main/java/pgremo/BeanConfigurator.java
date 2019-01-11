package pgremo;

import pgremo.logging.text.LevelPropertyEditor;
import pgremo.logging.text.ObjectInstancePropertyEditor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;

import static java.beans.Introspector.getBeanInfo;
import static java.beans.PropertyEditorManager.findEditor;
import static java.beans.PropertyEditorManager.registerEditor;

public class BeanConfigurator {
    static {
        registerEditor(Level.class, LevelPropertyEditor.class);
        registerEditor(Filter.class, ObjectInstancePropertyEditor.class);
        registerEditor(Formatter.class, ObjectInstancePropertyEditor.class);
    }

    public static void configure(Object target, Function<PropertyDescriptor, String> function) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        for (PropertyDescriptor descriptor : getBeanInfo(target.getClass()).getPropertyDescriptors()) {
            String value = function.apply(descriptor);
            if (value != null) {
                PropertyEditor editor = findEditor(descriptor.getPropertyType());
                editor.setAsText(value);
                descriptor.getWriteMethod().invoke(target, editor.getValue());
            }
        }
    }
}
