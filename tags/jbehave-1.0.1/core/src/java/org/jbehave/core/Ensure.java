/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package org.jbehave.core;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:damian@jbehave.org">Damian Guy</a>
 * @author <a href="mailto:manish@jbehave.org">Manish Shah</a>
 * @author <a href="mailto:steve@m3p.co.uk">Steve Freeman</a>
 */
public class Ensure {
	private static final UsingMatchers matchers = new UsingMatchers() {};
	
    /** should not be subclassed for behaviour classes but can be extended to add methods to namespace */
    protected Ensure() {}

    /** Ensure.that(something, isBlah()) */
    public static void that(Object arg, Matcher matcher) {
    	matchers.ensureThat(arg, matcher);
    }
    
    public static void that(Object arg, Matcher matcher, String message) {
    	matchers.ensureThat(arg, matcher, message);
	}

	public static void that(long arg, Matcher matcher) {
		matchers.ensureThat(arg, matcher, null);
    }
	public static void that(long arg, Matcher matcher, String message) {
		matchers.ensureThat(arg, matcher, message);
	}
    
    public static void that(double arg, Matcher matcher) {
    	matchers.ensureThat(arg, matcher, null);
    }
    public static void that(double arg, Matcher matcher, String message) {
    	matchers.ensureThat(arg, matcher, message);
    }
    
    public static void that(char arg, Matcher matcher) {
    	matchers.ensureThat(arg, matcher, null);
    }
    public static void that(char arg, Matcher matcher, String message) {
    	matchers.ensureThat(arg, matcher, message);
    }
    
    public static void that(boolean arg, Matcher matcher) {
    	matchers.ensureThat(arg, matcher);
    }
    public static void that(boolean arg, Matcher matcher, String message) {
    	matchers.ensureThat(arg, matcher, message);
    }

    /** Ensure.that(...) without matchers */
    public static void that(String message, boolean condition) {
    	matchers.ensureThat(condition, message);
    }

    /** Ensure.that(...) without matchers */
    public static void that(boolean condition) {
        matchers.ensureThat(condition, (String)null);
    }

    public static void not(String message, boolean condition) {
        Ensure.that(message, !condition);
    }
    
    public static void not(boolean condition) {
        Ensure.that(null, !condition);
    }

    /** like junit fail() */
	public static void impossible(String message) {
		matchers.fail(("\"Impossible\" behaviour: " + message));
	}
    
    // Verify.pending("...")
    public static void pending(String message) {
    	matchers.todo(message);
    }
    
    public static void pending() {
        throw new PendingException();
    }
    
    // throws exception
    public static void throwsException(Class exceptionType, Block block) throws Exception {
        try {
            block.run();
            matchers.fail("should have thrown " + exceptionType.getName());
        }
        catch (Exception e) {
            if (!exceptionType.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }
    }
    
    public static void doesNotThrowException(Block block) throws Exception {
        try {
            block.run();
        } catch (Exception e) {
            throw new VerificationException("Expected no exception", e);
        }
    }
    
    /** @deprecated use matchers */
    public static void instanceOf(Class type, Object instance) {
    	String message = "should be instance of " + type.getName()
    	+ " but it is " + (instance == null ? "null" : instance.getClass().getName());
    	that(message, type.isInstance(instance));
    }

    /** @deprecated use matchers */
    public static void equal(String message, boolean expected, boolean actual) {
        equal(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
    }

    /** @deprecated use matchers */
    public static void equal(boolean expected, boolean actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use matchers */
    public static void equal(String message, long expected, long actual) {
        if (expected != actual) {
            matchers.fail(message, new Long(expected), new Long(actual));
        }
    }

    /** @deprecated use matchers */
    public static void equal(long expected, long actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use matchers */
    public static void equal(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            matchers.fail(message, new Double(expected), new Double(actual));
        }
    }

    /** @deprecated use matchers */
    public static void equal(double expected, double actual, double delta) {
        equal(null, expected, actual, delta);
    }

    /** @deprecated use matchers */
    public static void equal(String message, Object expected, Object actual) {
        if (expected == null) {
            if (actual != null) {
            	matchers.fail(message, expected, actual);
            }
        }
        else if (!expected.equals(actual)) {
            matchers.fail(message, expected, actual);
        }
    }

    /** @deprecated use matchers */
    public static void equal(Object expected, Object actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use matchers */
	public static void sameInstance(String message, Object expected, Object actual) {
        if (expected != actual) matchers.fail(message, expected, actual);
	}
    
    /** @deprecated use matchers */
	public static void sameInstance(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}
    
    /** @deprecated use matchers */
	public static void identical(String message, Object expected, Object actual) {
        sameInstance(message, expected, actual);
	}
    
    /** @deprecated use matchers */
	public static void identical(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}
    
    /** @deprecated use matchers */
    public static void notNull(String message, Object actual) {
        not(message, actual == null);
    }

    /** @deprecated use matchers */
    public static void notNull(Object actual) {
        notNull(null, actual);
    }
}
