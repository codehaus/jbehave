/*
 * Created on 29-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.minilog;

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
    public abstract void debug(String message, Throwable cause);
    public abstract void info(String message, Throwable cause);
    public abstract void warn(String message, Throwable cause);
    public abstract void error(String message, Throwable cause);
    
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
    
    private interface LogFactory {
        Log logFor(Class type);
    }
    
    public static class JavaUtilLogFactory implements LogFactory {
        public Log logFor(final Class type) {
            return new Log() {
                private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(type.getName());
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
        public Log logFor(final Class type) {
            return new Log() {
                private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(type.getName());
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
        public Log logFor(Class type) {
            return new Log() {
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
            try {
                Class.forName("java.util.logging.Logger");
                setLogFactory(new JavaUtilLogFactory());
            }
            catch (ClassNotFoundException e) {
                setLogFactory(new NullLogFactory());
            }
        }
    }
    
    public static Log getLog(Object object) {
        return get(object.getClass());
    }
    
    public static Log get(Class type) {
        Log log = (Log)logs.get(type);
        if (log == null) {
            log = logFactory.logFor(type);
            logs.put(type, log);
        }
        return log;
    }
    
    /**
     * Set a new log factory
     * @return the old log factory
     */
    public static LogFactory setLogFactory(LogFactory logFactory) {
        LogFactory oldLogFactory = Log.logFactory;
        Log.logFactory = logFactory;
        return oldLogFactory;
    }
    public static void reset() {
        logs.clear();
    }
}
