/*
 * Created on 29-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minilog;

import java.util.HashMap;
import java.util.Map;

/**
 * Minimal decoupling of logging, insipred by Jakarta Commons Logging.
 * 
 * We look for log4j and then 1.4 logging. If neither are present, we probably don't care about logging.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class Log {
    private final String name;
    
    public abstract void debug(String message, Throwable cause);
    public abstract void info(String message, Throwable cause);
    public abstract void warn(String message, Throwable cause);
    public abstract void error(String message, Throwable cause);
    
    public Log(String name) {
        this.name = name;
    }
    
    public void debug(String message) {
        debug(message, null);
    }
    public void info(String message) {
        info(message, null);
    }
    public void warn(String message) {
        warn(message, null);
    }
    public void error(String message) {
        error(message, null);
    }
    
    /** Create a nested logger, like Avalon so Joe Walnes tells me */
    public Log childLog(String childName) {
        return getLog(name + "/" + childName);
    }
    
    public interface LogFactory {
        Log logFor(String name);
    }
    
    public static class JavaUtilLogFactory implements LogFactory {
        public Log logFor(final String name) {
            return new Log(name) {
                private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(name);
                public void debug(String message, Throwable cause) {
                    logger.log(java.util.logging.Level.FINE, message, cause);
                }
                public void info(String message, Throwable cause) {
                    logger.log(java.util.logging.Level.INFO, message, cause);
                }
                public void warn(String message, Throwable cause) {
                    logger.log(java.util.logging.Level.WARNING, message, cause);
                }
                public void error(String message, Throwable cause) {
                    logger.log(java.util.logging.Level.SEVERE, message, cause);
                }
            };
        }
    }
    
    public static class Log4jLogFactory implements LogFactory {
        public Log logFor(final String name) {
            return new Log(name) {
                private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(name);
                public void debug(String message, Throwable cause) {
                    logger.debug(message, cause);
                }
                public void info(String message, Throwable cause) {
                    logger.info(message, cause);
                }
                public void warn(String message, Throwable cause) {
                    logger.warn(message, cause);
                }
                public void error(String message, Throwable cause) {
                    logger.error(message, cause);
                }
            };
        }
    }
    
    public static class NullLogFactory implements LogFactory {
        public Log logFor(String name) {
            return new Log(name) {
                public void debug(String message, Throwable cause) {
                }
                public void error(String message, Throwable cause) {
                }
                public void info(String message, Throwable cause) {
                }
                public void warn(String message, Throwable cause) {
                }
            };
        }
    }
    
    private static final Map logs = new HashMap();
    private static LogFactory logFactory;
    
    static {
        try {
            Class.forName("org.apache.log4j.Logger");
            setLogFactory(new Log4jLogFactory());
        }
        catch (ClassNotFoundException e1) {
            // no log4j - try java.util.logging
            try {
                Class.forName("java.util.logging.Logger");
                setLogFactory(new JavaUtilLogFactory());
            }
            catch (ClassNotFoundException e) {
                // obviously not interested in logging
                setLogFactory(new NullLogFactory());
            }
        }
    }
    
    /** Convenience method - typically used as <tt>Log log = Log.getLog(this);</tt> */
    public static Log getLog(Object object) {
        return getLog(object.getClass());
    }
    
    /** Get a log for a particular class, using the class name as key */
    public static Log getLog(Class type) {
        return getLog(type.getName());
    }
    
    /** Get a log for a particular key */
    public static Log getLog(String name) {
        Log log = (Log)logs.get(name);
        if (log == null) {
            log = logFactory.logFor(name);
            logs.put(name, log);
        }
        return log;

    }
    
    /**
     * Set a new log factory - also clears cache of existing logs
     * 
     * @return the old log factory
     */
    public static LogFactory setLogFactory(LogFactory logFactory) {
        LogFactory oldLogFactory = Log.logFactory;
        Log.logFactory = logFactory;
        clearCache();
        return oldLogFactory;
    }
    
    /** Clear log cache */
    public static void clearCache() {
        logs.clear();
    }
}
