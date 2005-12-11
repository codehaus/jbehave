/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.core;

import jbehave.core.exception.PendingException;
import jbehave.core.minimock.Constraint;
import jbehave.core.minimock.UsingConstraints;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:damian@jbehave.org">Damian Guy</a>
 * @author <a href="mailto:manish@jbehave.org">Manish Shah</a>
 * @author <a href="mailto:steve@m3p.co.uk">Steve Freeman</a>
 */
public class Ensure {
	private static final UsingConstraints constraints = new UsingConstraints() {};
	
    /** should not be subclassed for behaviour classes but can be extended to add methods to namespace */
    protected Ensure() {}

    /** Ensure.that(something, isBlah()) */
    public static void that(Object arg, Constraint constraint) {
    	constraints.ensureThat(arg, constraint);
    }
    
    public static void that(Object arg, Constraint constraint, String message) {
    	constraints.ensureThat(arg, constraint, message);
	}

	public static void that(long arg, Constraint constraint) {
		constraints.ensureThat(arg, constraint, null);
    }
	public static void that(long arg, Constraint constraint, String message) {
		constraints.ensureThat(arg, constraint, message);
	}
    
    public static void that(double arg, Constraint constraint) {
    	constraints.ensureThat(arg, constraint, null);
    }
    public static void that(double arg, Constraint constraint, String message) {
    	constraints.ensureThat(arg, constraint, message);
    }
    
    public static void that(char arg, Constraint constraint) {
    	constraints.ensureThat(arg, constraint, null);
    }
    public static void that(char arg, Constraint constraint, String message) {
    	constraints.ensureThat(arg, constraint, message);
    }
    
    public static void that(boolean arg, Constraint constraint) {
    	constraints.ensureThat(arg, constraint);
    }
    public static void that(boolean arg, Constraint constraint, String message) {
    	constraints.ensureThat(arg, constraint, message);
    }

    /** Ensure.that(...) without constraints */
    public static void that(String message, boolean condition) {
    	constraints.ensureThat(condition, message);
    }

    /** Ensure.that(...) without constraints */
    public static void that(boolean condition) {
        constraints.ensureThat(condition, (String)null);
    }

    public static void not(String message, boolean condition) {
        Ensure.that(message, !condition);
    }
    
    public static void not(boolean condition) {
        Ensure.that(null, !condition);
    }

    /** like junit fail() */
	public static void impossible(String message) {
		constraints.fail(("\"Impossible\" behaviour: " + message));
	}
    
    // Verify.pending("...")
    public static void pending(String message) {
    	constraints.todo(message);
    }
    
    public static void pending() {
        throw new PendingException();
    }
    
    // throws exception
    public static void throwsException(Class exceptionType, Block block) throws Exception {
        try {
            block.execute();
            constraints.fail("should have thrown " + exceptionType.getName());
        }
        catch (Exception e) {
            if (!exceptionType.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }
    }
    
    /** @deprecated use constraints */
    public static void instanceOf(Class type, Object instance) {
    	String message = "should be instance of " + type.getName()
    	+ " but it is " + (instance == null ? "null" : instance.getClass().getName());
    	that(message, type.isInstance(instance));
    }

    /** @deprecated use constraints */
    public static void equal(String message, boolean expected, boolean actual) {
        equal(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
    }

    /** @deprecated use constraints */
    public static void equal(boolean expected, boolean actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use constraints */
    public static void equal(String message, long expected, long actual) {
        if (expected != actual) {
            constraints.fail(message, new Long(expected), new Long(actual));
        }
    }

    /** @deprecated use constraints */
    public static void equal(long expected, long actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use constraints */
    public static void equal(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            constraints.fail(message, new Double(expected), new Double(actual));
        }
    }

    /** @deprecated use constraints */
    public static void equal(double expected, double actual, double delta) {
        equal(null, expected, actual, delta);
    }

    /** @deprecated use constraints */
    public static void equal(String message, Object expected, Object actual) {
        if (expected == null) {
            if (actual != null) {
            	constraints.fail(message, expected, actual);
            }
        }
        else if (!expected.equals(actual)) {
            constraints.fail(message, expected, actual);
        }
    }

    /** @deprecated use constraints */
    public static void equal(Object expected, Object actual) {
        equal(null, expected, actual);
    }

    /** @deprecated use constraints */
	public static void sameInstance(String message, Object expected, Object actual) {
        if (expected != actual) constraints.fail(message, expected, actual);
	}
    
    /** @deprecated use constraints */
	public static void sameInstance(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}
    
    /** @deprecated use constraints */
	public static void identical(String message, Object expected, Object actual) {
        sameInstance(message, expected, actual);
	}
    
    /** @deprecated use constraints */
	public static void identical(Object expected, Object actual) {
        sameInstance(null, expected, actual);
	}
    
    /** @deprecated use constraints */
    public static void notNull(String message, Object actual) {
        not(message, actual == null);
    }

    /** @deprecated use constraints */
    public static void notNull(Object actual) {
        notNull(null, actual);
    }
}
