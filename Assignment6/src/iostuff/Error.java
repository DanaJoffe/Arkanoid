package iostuff;

/**
 * @author joffeda.
 */
public class Error {
    /**
     * exit the program.
     * @param error why the error occurred, or- what Exception was thrown.
     * @param address where did the error occurred, or- where was the Exception thrown.
     */
    public static void printAndExit(String error, String address) {
        System.out.println("---\n" + "ERROR\n" + error);
        System.out.println("Occurred at: " + address + "\n---");
        System.exit(1);
    }

    /**
     *
     * @param error why the error occurred, or- what Exception was thrown.
     * @param address where did the error occurred, or- where was the Exception thrown.
     */
    public static void print(String error, String address) {
        System.out.println("---\n" + "Warning\n" + error);
        System.out.println("Occurred at: " + address + "\n---");
    }
}