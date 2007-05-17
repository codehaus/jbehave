/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package org.jbehave.core;

import org.jbehave.core.matchers.UsingEqualityMatchers;
import org.jbehave.core.matchers.UsingExceptions;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:damian@jbehave.org">Damian Guy</a>
 * @author <a href="mailto:manish@jbehave.org">Manish Shah</a>
 * @author <a href="mailto:steve@m3p.co.uk">Steve Freeman</a>
 */
public class Ensure {
	private static final String NL = System.getProperty("line.separator");
	
    /** should not be subclassed for behaviour classes but can be extended to add methods to namespace */
    protected Ensure() {}

    /** Ensure.that(something, isBlah()) */
    public static void that(Object arg, Matcher matcher) {
    	that(arg, matcher, null);
    }  
    
	public static void that(Object arg, Matcher matcher, String message) {
		if (matcher instanceof CustomMatcher) {
	    	if (!matcher.matches(arg)) {
	    		UsingExceptions.fail("Expected: " +
	                    (message != null ? "[" + message + "] ": "") + NL +
	                    matcher + NL +
	                    "but got: " + NL + ((CustomMatcher)matcher).describe(arg));
	        }
		} else if (!matcher.matches(arg)) {
    		UsingExceptions.fail("Expected: " +
    				(message != null ? "[" + message + "] " : "") + NL + 
    				matcher + NL +
    				"but got: " + NL + arg);
    	}
	}

	public static void that(long arg, Matcher matcher) {
		that(arg, matcher, null);
    }
	public static void that(long arg, Matcher matcher, String message) {
		that(new Long(arg), matcher, message);
	}
    
    public static void that(double arg, Matcher matcher) {
    	that(arg, matcher, null);
    }
    public static void that(double arg, Matcher matcher, String message) {
    	that(new Double(arg), matcher, message);
    }
    
    public static void that(char arg, Matcher matcher) {
    	that(arg, matcher, null);
    }
    public static void that(char arg, Matcher matcher, String message) {
    	that(new Character(arg), matcher, message);
    }
    
    public static void that(boolean arg, Matcher matcher) {
    	that(arg, matcher, null);
    }
    public static void that(boolean arg, Matcher matcher, String message) {
    	that(Boolean.valueOf(arg), matcher, message);
    }

    public static void that(String message, boolean condition) {
    	that(condition, message);
    }

    /** Ensure.that(...) without matchers */
    public static void that(boolean condition) {
        that(condition, "Expected condition was not met");
    }

	public static void that(boolean condition, String message) {
    	if (!condition) {
    		UsingExceptions.fail(message);
    	}
	}    
    
    public static void not(String message, boolean condition) {
        that(message, !condition);
    }
    
    public static void not(boolean condition) {
        that(!condition);
    }

    /** like junit fail() */
	public static void impossible(String message) {
		UsingExceptions.fail(("\"Impossible\" behaviour: " + message));
	}
    
    public static void pending(String message) {
    	UsingExceptions.todo(message);
    }
    
    public static void pending() {
        UsingExceptions.pending();
    }
    
    // throws exception
    public static void throwsException(Class exceptionType, Block block) throws Exception {
        Exception exception = UsingExceptions.runAndCatch(exceptionType, block);
        that(exception, UsingEqualityMatchers.isNotNull());
    }
    
    public static void doesNotThrowException(Block block) throws Exception {
        Exception exception = UsingExceptions.runAndCatch(Exception.class, block);
        that(exception, UsingEqualityMatchers.isNull());
    }
    
    /** @deprecated use UsingMatchers or UsingEqualityMatchers.isA */
    public static void instanceOf(Class type, Object instance) {
    	that(instance, UsingEqualityMatchers.isA(type));
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(String message, boolean expected, boolean actual) {
    	that(actual, UsingEqualityMatchers.eq(expected), message);
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(boolean expected, boolean actual) {
    	that(actual, UsingEqualityMatchers.eq(expected));
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(String message, long expected, long actual) {
        that(actual, UsingEqualityMatchers.eq(expected), message);
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(long expected, long actual) {
    	that(actual, UsingEqualityMatchers.eq(expected));
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(String message, double expected, double actual, double delta) {
    	that(actual, UsingEqualityMatchers.eq(expected, delta), message);
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(double expected, double actual, double delta) {
    	that(actual, UsingEqualityMatchers.eq(expected));
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(String message, Object expected, Object actual) {
    	that(actual, UsingEqualityMatchers.eq(expected), message);
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.eq */
    public static void equal(Object expected, Object actual) {
    	that(actual, UsingEqualityMatchers.eq(expected));
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.is */
	public static void sameInstance(String message, Object expected, Object actual) {
		that(actual, UsingEqualityMatchers.is(expected), message);
	}
    
    /** @deprecated use UsingMatchers or UsingEqualityMatchers.is */
	public static void sameInstance(Object expected, Object actual) {
		that(actual, UsingEqualityMatchers.is(expected));
	}
    
    /** @deprecated use UsingMatchers or UsingEqualityMatchers.is */
	public static void identical(String message, Object expected, Object actual) {
		that(actual, UsingEqualityMatchers.is(expected), message);
	}
    
    /** @deprecated use UsingMatchers or UsingEqualityMatchers.is */
	public static void identical(Object expected, Object actual) {
		that(actual, UsingEqualityMatchers.is(expected));
	}
    
    /** @deprecated use UsingMatchers or UsingEqualityMatchers.isNotNull */
    public static void notNull(String message, Object actual) {
		that(actual, UsingEqualityMatchers.isNotNull(), message);
    }

    /** @deprecated use UsingMatchers or UsingEqualityMatchers.isNotNull */
    public static void notNull(Object actual) {
		that(actual, UsingEqualityMatchers.isNotNull());
    }


}
