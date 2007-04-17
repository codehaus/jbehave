package org.jbehave.core.mock;

import java.util.Collection;

import org.jbehave.core.Block;
import org.jbehave.core.Ensure;
import org.jbehave.core.matchers.UsingCollectionMatchers;
import org.jbehave.core.matchers.UsingEqualityMatchers;
import org.jbehave.core.matchers.UsingExceptions;
import org.jbehave.core.matchers.UsingLogicalMatchers;
import org.jbehave.core.matchers.UsingStringMatchers;


/**
 * <p>Matchers are used by the MiniMock framework, 
 * {@link org.jbehave.core.Ensure}, {@link UsingMatchers} and 
 * {@link org.jbehave.core.minimock.UsingMiniMock} to
 * verify or ensure behaviours. Matchers are also
 * used by the default elements of the Story framework.</p>
 * 
 * <p>Some simple matchers are provided by
 * this class.</p>
 * 
 * <p>In any domain, there will be more complex, specific
 * matchers which can be reused across behaviours. CustomMatcher
 * may be extended to provide these behaviours, or Matcher
 * may be implemented. If you provide a useful
 * description, either to the constructor of the CustomMatcher
 * or by overriding <code>toString()</code> in your Matcher,
 * a useful message will be given should the Matcher not match.</p>
 * 
 * <p>You may find it more useful to extend or delegate to 
 * {@link org.jbehave.core.minimock.UsingMiniMock} than to UsingMatchers.
 */
public abstract class UsingMatchers {
    
    private static final String NL = System.getProperty("line.separator");

    /** @deprecated Use org.jbehave.core.matchers.CustomMatcher instead */
    public static abstract class CustomMatcher extends UsingMatchers implements Matcher {
        private final String description;

		public CustomMatcher(String description) {
			this.description = description;
        }

        public String toString() {
            return description;
        }
        
        public CustomMatcher and(Matcher that) {
            return UsingLogicalMatchers.and(this, that);
        }
        
        public CustomMatcher or(Matcher that) {
            return UsingLogicalMatchers.or(this, that);
        }
        
        public String describe(Object arg) {
            return "" + arg;
        }        
    }
    
	/** ensures object is not null */
    public org.jbehave.core.matchers.CustomMatcher isNotNull() {
        return UsingEqualityMatchers.isNotNull();
    }
    
    public CustomMatcher isNull() {
        return UsingEqualityMatchers.isNull();
    }
    
	/** ensures object equals expected value */    
	public CustomMatcher eq(Object expectedArg) {
	    return UsingEqualityMatchers.eq(expectedArg);
	}

	/** eq(primitive) for float and double */
	public CustomMatcher eq(double expectedArg, double delta) {
	    return UsingEqualityMatchers.eq(expectedArg, delta);
	}

	/** eq(primitive) for float and double */
	public CustomMatcher eq(double expectedArg) {
		return UsingEqualityMatchers.eq(expectedArg);
	}

	/** eq(primitive) for byte, short, integer and long */	
	public CustomMatcher eq(long expectedArg) {
	    return UsingEqualityMatchers.eq(expectedArg);
	}

	/** eq(primitive) for char - note {@link Character} is not a {@link Number} */
	public CustomMatcher eq(char expectedArg) {
	    return UsingEqualityMatchers.eq(expectedArg);
	}

	/** eq(primitive) for boolean */
	public CustomMatcher eq(boolean expectedArg) {
	    return UsingEqualityMatchers.eq(expectedArg);
	}

	public CustomMatcher is(Object expectedArg) {
	    return UsingEqualityMatchers.is(expectedArg);
    }

	public CustomMatcher sameInstanceAs(final Object expectedArg) {
	    return UsingEqualityMatchers.sameInstanceAs(expectedArg);
	}

	public CustomMatcher anything() {
	    return UsingEqualityMatchers.anything();
	}
    
    public CustomMatcher nothing() {
        return UsingEqualityMatchers.nothing();
    }

	public CustomMatcher a(Class type) {
	    return UsingEqualityMatchers.a(type);
	}

	public CustomMatcher isA(final Class type) {
	    return UsingEqualityMatchers.isA(type);
	}
	
	public CustomMatcher startsWith(String fragment) {
	    return UsingStringMatchers.startsWith(fragment);
	}
	
	public CustomMatcher endsWith(String fragment) {
	    return UsingStringMatchers.endsWith(fragment);
	}
	
	public CustomMatcher contains(String fragment) {
	    return UsingStringMatchers.contains(fragment);
	}
    
    public CustomMatcher collectionContaining(CustomMatcher[] matchers) {
        return UsingCollectionMatchers.collectionContaining(matchers);
    }
    
    public CustomMatcher collectionContaining(final CustomMatcher matcher) {
        return UsingCollectionMatchers.collectionContaining(matcher);
    }
    
    public CustomMatcher collectionContaining(Object object) {
        return UsingCollectionMatchers.collectionContaining(object);
    }
    
    public CustomMatcher collectionContaining(Object object1, Object object2) {
        return UsingCollectionMatchers.collectionContaining(object1, object2);
    }
    
	public CustomMatcher and(Matcher a, Matcher b) {
	    return UsingLogicalMatchers.and(a, b);
	}

	public CustomMatcher both(Matcher a, Matcher b) {
	    return UsingLogicalMatchers.both(a, b);
	}

	public CustomMatcher or(Matcher a, Matcher b) {
	    return UsingLogicalMatchers.or(a, b);
	}

	public CustomMatcher either(Matcher a, Matcher b) {
	    return UsingLogicalMatchers.either(a, b);
	}

	public CustomMatcher not(Matcher c) {
	    return UsingLogicalMatchers.not(c);
	}
	
    public CustomMatcher isContainedIn(final Collection collection) {
        return UsingCollectionMatchers.isContainedIn(collection);
    }
    
    public void ensureThat(Object arg, CustomMatcher matcher, String message) {
        Ensure.that(arg, matcher, message);
    }
    
    public void ensureThat(Object arg, Matcher matcher, String message) {
    	Ensure.that(arg, matcher, message);
	}
    
	public void ensureThat(Object arg, Matcher matcher) {
		Ensure.that(arg, matcher);
	}
	
	public void ensureThat(long arg, Matcher matcher, String message) {
		Ensure.that(arg, matcher, message);
	}
	public void ensureThat(long arg, Matcher matcher) {
		Ensure.that(arg, matcher);
	}
    
    public void ensureThat(double arg, Matcher matcher, String message) {
    	Ensure.that(arg, matcher, message);
    }
    
    public void ensureThat(double arg, Matcher matcher) {
    	Ensure.that(arg, matcher);
    }
    
    public void ensureThat(char arg, Matcher matcher, String message) {
    	Ensure.that(arg, matcher, message);
    }
    public void ensureThat(char arg, Matcher matcher) {
    	Ensure.that(arg, matcher);
    }
    
    public void ensureThat(boolean arg, Matcher matcher, String message) {
    	Ensure.that(arg, matcher, message);
    }
    
    public void ensureThat(boolean arg, Matcher matcher) {
    	Ensure.that(arg, matcher);
    }
    
    /**
     * @return a caught exception assignable from the given type, or null if no such exception was caught
     */
    public Exception runAndCatch(Class exceptionType, Block block) throws Exception {
        return UsingExceptions.runAndCatch(exceptionType, block);
    }
    
    public void ensureThat(boolean condition, String message) {
    	Ensure.that(condition, message);
    }
    
    public void that(String message, boolean condition) {
    	Ensure.that(message, condition);
    }

    /** ensure(...) without matchers */
    public void ensureThat(boolean condition) {
    	Ensure.that(condition);
    }

    public void fail(String message) {
        UsingExceptions.fail(message);
    }
    
    public void fail(String message, Exception e) {
        UsingExceptions.fail(message, e);
    }

    public void fail(String message, Object expected, Object actual) {
        UsingExceptions.fail(message, expected, actual);
    }

	public void pending(String message) {
	    UsingExceptions.pending(message);
	}
	
	public void pending() {
	    UsingExceptions.pending();
	}
    
    public void todo() {
        UsingExceptions.todo(); 
    }
    
    public void todo(String message) {
        UsingExceptions.todo(message);
    }
}
