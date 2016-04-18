package weather.common.utilities;

/**
 * Singleton for printing debug statements to the console.
 *
 * @author Eric Subach (2011)
 */
public class Debug {
    private static boolean isEnabled;
    //private static boolean isInitialized;

    static {
        /* This method doesn't work because it uses an optional debug protocol
         * (JDWP) which is not installed.
         *
         * A possible way of conditional compilation is through Ant:
         * http://weblogs.java.net/blog/schaefa/archive/2005/01/how_to_do_condi_1.html
         */
        //isEnabled = java.lang.management.ManagementFactory.getRuntimeMXBean().
        //    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

        //Currently we want to Debug
        isEnabled = true;
    }

    
    /**
     * Returns whether we are debugging or not.
     * 
     * @return true if debugging is enabled, false otherwise
     */
    public static boolean isEnabled () {
        return (isEnabled);
    }

    /**
     * Enable or disable debugging.
     *
     * @return true if debugging was already enabled, false otherwise
     */
    public static boolean setEnabled (boolean value) {
        boolean oldValue = isEnabled;
        isEnabled = value;

        return (oldValue);
    }

    /**
     * Toggle the status of debugging.
     */
    public static void toggle () {
        isEnabled = !isEnabled;
    }

    /**
     * Print string to the console.
     * 
     * @param str the debugging message to be printed to the error stream
     */
    public static void print (String str) {
        if (isEnabled) {
            System.err.print (str);
        }
    }

    /**
     * Print object to the console.
     * 
     * @param obj the debugging object to be printed to the error stream
     */
    public static void print (Object obj) {
        if (isEnabled) {
            System.err.print (obj);
        }
    }

    /**
     * Print integer to the console.
     * 
     * @param i the debugging error number to be printed to the error stream
     */
    public static void print (int i) {
        if (isEnabled) {
            System.err.print (i);
        }
    }

    /**
     * Print string to the console with newline.
     * 
     * @param str the debugging message to be printed to the error stream
     */
    public static void println (String str) {
        print (str + '\n');
    }
    
    /**
     * Print string to the console with newline.
     */
    public static void println () {
        print ("\n");
    }

    /**
     * Print integer to the console with newline.
     * 
     * @param i the debugging error number to be printed to the error stream
     */
    public static void println (int i) {
        print (i + '\n');
    }

    /**
     * Print object to the console with newline.
     * 
     * @param obj the debugging object to be printed to the error stream
     */
    public static void println (Object obj) {
        print (obj.toString() + '\n');
    }
}
