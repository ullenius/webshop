package se.anosh.webshop.util;

import java.util.Date;

public class Logger {
	
	private static final boolean enabled = true;
	
	private Logger() {
		throw new AssertionError("Class cannot be instantiated");
	}
	
	public static void log(final CharSequence message) {
		
		if (enabled)
			System.out.println(new Date() + " : + " + message);
	}
	
	public boolean isEnabled() {
		return enabled;
	}

}
