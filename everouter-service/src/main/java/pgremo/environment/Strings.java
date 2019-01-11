package pgremo.environment;

public class Strings {
    public static String uncapitalize(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    public static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
