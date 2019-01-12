package pgremo.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import static java.beans.Introspector.getBeanInfo;
import static java.beans.PropertyEditorManager.findEditor;

public class BeanConfigurator {
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
