/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:damian@jbehave.org">Damian Guy</a>
 * @author <a href="mailto:manish@jbehave.org">Manish Shah</a>
 * @author <a href="mailto:steve@m3p.co.uk">Steve Freeman</a>
 */
public class Verify {
    /** should not be subclassed for behaviour classes but can be extended to add methods to namespace */
    private Verify() {}

    // boolean
    public static void equal(String message, boolean expected, boolean actual) {
        equal(message, toBoolean(expected), toBoolean(actual));
    }

    public static void equal(boolean expected, boolean actual) {
        equal(null, expected, actual);
    }

    // long (also byte, char, int)
    public static void equal(String message, long expected, long actual) {
        if (expected != actual) {
            fail(message, new Long(expected), new Long(actual));
        }
    }

    public static void equal(long expected, long actual) {
        equal(null, expected, actual);
    }

    // double (also float)
    public static void equal(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            fail(message, new Double(expected), new Double(actual));
        }
    }

    public static void equal(double expected, double actual, double delta) {
        equal(null, expected, actual, delta);
    }

    // Object
    public static void equal(String message, Object expected, Object actual) {
        if (expected == null) {
            if (actual != null) fail(message, expected, actual);
        }
        else if (!expected.equals(actual)) {
            fail(message, expected, actual);
        }
    }

    public static void equal(Object expected, Object actual) {
        equal(null, expected, actual);
    }

    // Identity
	public static void sameInstance(String message, Object expected, Object actual) {
        if (expected != actual) fail(message, expected, actual);
	}
    
	public static void sameInstance(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}
    
	public static void identical(String message, Object expected, Object actual) {
        sameInstance(message, expected, actual);
	}
    
	public static void identical(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}

    // Verify.that(...)
    public static void that(String message, boolean condition) {
        if (!condition) {
            fail(message != null ? message : "Expected condition was not met");
        }
    }

    public static void that(boolean condition) {
        that(null, condition);
    }
    
    // not
    public static void not(boolean condition) {
        equal(Boolean.FALSE, toBoolean(condition));
    }

    public static void not(String message, boolean condition) {
        equal(message, Boolean.FALSE, toBoolean(condition));
    }

    // not null (use equal(null, x) for null)
    public static void notNull(String message, Object actual) {
        not(message, actual == null);
    }

    public static void notNull(Object actual) {
        notNull(null, actual);
    }

    // like junit fail()
	public static void impossible(String message) {
        throw new VerificationException(("\"Impossible\" behaviour: " + message));
	}
    
    private static Boolean toBoolean(boolean value) {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }

    private static void fail(String message) {
        throw new VerificationException(message);
    }

    private static void fail(String message, Object expected, Object actual) {
        throw new VerificationException(message, expected, actual);
    }

    // Verify.pending("...")
    public static void pending(String message) {
        throw new PendingException(message);
    }
    
    public static void pending() {
        throw new PendingException();
    }

    // instanceof
    public static void instanceOf(Class type, Object instance) {
        that("should be instance of " + type.getName(), type.isInstance(instance));
    }
    
    // throws exception
    public static void throwsException(Class exceptionType, Block block) throws Exception {
        try {
            block.execute();
            fail("should have thrown " + exceptionType.getName());
        }
        catch (Exception e) {
            if (!exceptionType.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }
    }
}
