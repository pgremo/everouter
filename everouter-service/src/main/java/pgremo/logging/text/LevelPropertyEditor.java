package pgremo.logging.text;

import java.beans.PropertyEditorSupport;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.logging.Level.*;

public class LevelPropertyEditor extends PropertyEditorSupport {
    private static Map<String, Level> levels = Stream.of(new Level[]{
            OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL
    })
            .collect(Collectors.toMap(Level::getName, identity()));

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(levels.get(text));
    }
}
