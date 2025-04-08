public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("\u001B[32m[INFO] " + message + "\u001B[0m");
    }

    @Override
    public void error(String message) {
        System.out.println("\u001B[31m[ERROR] " + message + "\u001B[0m");
    }
}