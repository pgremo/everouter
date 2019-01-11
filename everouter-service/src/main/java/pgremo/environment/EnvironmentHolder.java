package pgremo.environment;

public class EnvironmentHolder {
    private static Environment environment;

    public static Environment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(Environment environment) {
        EnvironmentHolder.environment = environment;
    }
}
