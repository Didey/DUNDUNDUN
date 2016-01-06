package com.didey.dun.engine;

public class Logger {
	
	public static int loggingLevel = 0; //0 - All, 1 - Warn, 2 - Warn + Debug, 4 - None 

	public static void info(String info){
		if (loggingLevel == 0) {
			System.out.println("[INFO] " + info);
		}
	}
	
	public static void debug(String debug){
		if (loggingLevel == 0 || loggingLevel == 2) {
			System.out.println("[DEBUG] " + debug);
		}
	}
	
	public static void warn(String warn){
		if (loggingLevel == 0 || loggingLevel == 1 || loggingLevel == 2) {
			System.out.println("[WARN] " + warn);
		}
	}
}
