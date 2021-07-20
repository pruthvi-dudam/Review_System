package product.review.sys;

import java.io.Console;
import java.util.logging.Level;

public class Logger {
	
	private final static boolean isDebug = true; 
		
	
	/*
	 * Prints debug level logs onto the screen.
	 */
	public static void debug(final String m) {
		if (isDebug)
			System.out.println(" => DEB " + m);
	}
	
	/*
	 * Output message onto the screen
	 */
	public static void output(final String m) {
		System.out.println(m);
	}
	
	/*
	 * Print error level logs on to the screen
	 */
	public static void error(final String mes) {
		if (isDebug)
			System.err.println(" => ERR " + mes);
	}
	
	/*
	 * Print error level logs on to the screen with exception message.
	 */
	public static void error(final Throwable tr, final String m) {
		if (isDebug)
			System.err.println(" => ERR " + m + (tr != null?tr.getMessage():""));
	}
	
	/*
	 * Reads user input from console.
	 */
	public static String input() {
		
		Console console = System.console();
		return console.readLine(" => ");
	}
	
	/*
	 * Set mongo db log level to Warning and above.
	 */
	public static void hideMongoDbLogs() {
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.connection").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.management").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.cluster").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.protocol.insert").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.protocol.query").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.mongodb.driver.protocol.update").setLevel(Level.WARNING);
	}
	
}
