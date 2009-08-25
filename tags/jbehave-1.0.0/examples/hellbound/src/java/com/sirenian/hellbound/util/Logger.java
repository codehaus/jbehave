package com.sirenian.hellbound.util;

public class Logger {
    
    private static final boolean DEBUG = System.getProperty("DEBUG") != null;

    public static void debug(Object source, String message) {
        if (DEBUG) {
            System.out.println(source.getClass().getName() + ": " + message);
        }
    }

}
