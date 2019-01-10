package pgremo;

@Profile("db-load")
public class DBLoader implements Runner {
    @Override
    public void run(String... args) {
        System.out.println("HI");
    }
}
