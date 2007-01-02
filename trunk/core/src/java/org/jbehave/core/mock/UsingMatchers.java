package org.jbehave.core.mock;

import java.util.Collection;

import org.jbehave.core.Block;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;


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
	public abstract static class CustomMatcher extends UsingMatchers implements Matcher {
		private final String description;

		public CustomMatcher(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
		
		public CustomMatcher and(Matcher that) {
			return and(this, that);
		}
		public CustomMatcher or(Matcher that) {
			return or(this, that);
		}
	}

    /** ensures object is not null */
    public CustomMatcher isNotNull() {
        return new CustomMatcher("object not null") {
            public boolean matches(Object arg) {
                return arg != null;
            }
        };
    }
    

    
    public CustomMatcher isNull() {
        return new CustomMatcher("object is null") {
            public boolean matches(Object arg) {
                return arg == null;
            }
        };
    }
    
	/** ensures object equals expected value */
	public CustomMatcher eq(final Object expectedArg) {
	    return new CustomMatcher("equal to <" + expectedArg + ">") {
	        public boolean matches(Object arg) {
	            return arg == null ? expectedArg == null : arg.equals(expectedArg);
	        }
	    };
	}

	/** eq(primitive) for float and double */
	public CustomMatcher eq(final double expectedArg, final double delta) {
	    return new CustomMatcher("floating point number equal to " + expectedArg) {
	        public boolean matches(Object arg) {
	            double value = ((Number) arg).doubleValue();
				return Math.abs(expectedArg - value) <= delta;
	        }
	    };
	}

	/** eq(primitive) for float and double */
	public CustomMatcher eq(final double expectedArg) {
		return eq(expectedArg, 0.0);
	}
	
	/** eq(primitive) for byte, short, integer and long */
	public CustomMatcher eq(final long expectedArg) {
	    return new CustomMatcher("integer type equal to " + expectedArg) {
	        public boolean matches(Object arg) {
	            Number n = (Number)arg;
	            return n.longValue() == expectedArg;
	        }          
	    };
	}

	/** eq(primitive) for char - note {@link Character} is not a {@link Number} */
	public CustomMatcher eq(final char expectedArg) {
	    return new CustomMatcher("character equal to '" + expectedArg + "'") {
	        public boolean matches(Object arg) {
	            Character n = (Character)arg;
	            return n.charValue() == expectedArg;
	        }          
	    };
	}

	/** eq(primitive) for boolean */
	public CustomMatcher eq(final boolean expectedArg) {
	    return new CustomMatcher("boolean " + expectedArg) {
	        public boolean matches(Object arg) {
	            Boolean n = (Boolean)arg;
	            return n.booleanValue() == expectedArg;
	        }          
	    };
	}

	public CustomMatcher sameInstanceAs(final Object expectedArg) {
	    return new CustomMatcher("same instance as <" + expectedArg.toString() + ">") {
	        public boolean matches(Object arg) {
	            return expectedArg == arg;
	        }
	    };
	}

	public CustomMatcher anything() {
	    return new CustomMatcher("anything") {
	        public boolean matches(Object arg) {
	            return true;
	        }
	    };
	}

	public CustomMatcher a(final Class type) {
	    return isA(type);
	}

	public CustomMatcher isA(final Class type) {
	    return new CustomMatcher("object of type " + type.getName()) {
	        public boolean matches(Object arg) {
	            return type.isInstance(arg);
	        }
	    };
	}
	
	public CustomMatcher startsWith(final String fragment) {
	    return new CustomMatcher("string starting with <" + fragment + ">") {
	        public boolean matches(Object arg) {
	            return ((String)arg).startsWith(fragment);
	        }
	    };
	}
	
	public CustomMatcher endsWith(final String fragment) {
		return new CustomMatcher("string ending with <" + fragment + ">") {
			public boolean matches(Object arg) {
				return ((String)arg).endsWith(fragment);
			}
		};
	}
	
	public CustomMatcher contains(final String fragment) {
		return new CustomMatcher("string containing <" + fragment + ">") {
			public boolean matches(Object arg) {
				return arg.toString().indexOf(fragment) != -1;
			}
		};
	}

	public CustomMatcher and(final Matcher a, final Matcher b) {
	    return new CustomMatcher("(" + a + " and " + b + ")") {
	        public boolean matches(Object arg) {
	            return a.matches(arg) && b.matches(arg);
	        }
	    };
	}

	public CustomMatcher both(final Matcher a, final Matcher b) {
	    return and(a, b);
	}

	public CustomMatcher or(final Matcher a, final Matcher b) {
	    return new CustomMatcher("(" + a + " or " + b + ")") {
	        public boolean matches(Object arg) {
	            return a.matches(arg) || b.matches(arg);
	        }
	    };
	}

	public CustomMatcher either(final Matcher a, final Matcher b) {
	    return either(a, b);
	}

	public CustomMatcher not(final Matcher c) {
	    return new CustomMatcher("not (" + c + ")") {
	        public boolean matches(Object arg) {
	            return !c.matches(arg);
	        }
	    };
	}
	
    public CustomMatcher isContainedIn(final Collection collection) {
        return new CustomMatcher("is contained in " + collection) {
            public boolean matches(Object arg) {
                return collection.contains(arg);
            }
        };
    }    
    public void ensureThat(Object arg, Matcher matcher, String message) {
    	if (!matcher.matches(arg)) {
    		fail("\nExpected: " +
    				(message != null ? "[" + message + "] " : "") +
    				matcher +
    				"\nbut got:  <" + arg + ">");
    	}
	}
    
	public void ensureThat(Object arg, Matcher matcher) {
		ensureThat(arg, matcher, null);
	}
	
	public void ensureThat(long arg, Matcher matcher, String message) {
		ensureThat(new Long(arg), matcher, message);
	}
	public void ensureThat(long arg, Matcher matcher) {
		ensureThat(new Long(arg), matcher, null);
	}
    
    public void ensureThat(double arg, Matcher matcher, String message) {
    	ensureThat(new Double(arg), matcher, message);
    }
    public void ensureThat(double arg, Matcher matcher) {
    	ensureThat(arg, matcher, null);
    }
    
    public void ensureThat(char arg, Matcher matcher, String message) {
    	ensureThat(new Character(arg), matcher, message);
    }
    public void ensureThat(char arg, Matcher matcher) {
    	ensureThat(arg, matcher, null);
    }
    
    public void ensureThat(boolean arg, Matcher matcher, String message) {
    	ensureThat(Boolean.valueOf(arg), matcher, message);
    }
    public void ensureThat(boolean arg, Matcher matcher) {
    	ensureThat(arg, matcher, null);
    }
    
    /**
     * @return a caught exception assignable from the given type, or null if no such exception was caught
     */
    public Exception runAndCatch(Class exceptionType, Block block) throws Exception {
        try {
            block.run();
        }
        catch (Exception e) {
            if (exceptionType.isAssignableFrom(e.getClass())) {
                return e;
            } else {
                throw e;
            }
        }
        return null;
    }
    
    /** ensure(...) without matchers */
    public void ensureThat(boolean condition, String message) {
    	if (!condition) {
    		fail(message + ": expected condition was not met");
    	}
    }

    /** ensure(...) without matchers */
    public void ensureThat(boolean condition) {
        if (!condition) {
        	fail("Expected condition was not met");
        }
    }

    public void fail(String message) {
        throw new VerificationException(message);
    }
    
    public void fail(String message, Exception e) {
        throw new VerificationException(message, e);
    }

    public void fail(String message, Object expected, Object actual) {
        throw new VerificationException(message, expected, actual);
    }

	public void pending(String message) {
		throw new PendingException(message);
	}
	
	public void pending() {
		pending("TODO");
	}
    
    public void todo() { pending(); }
    public void todo(String message) { pending(message); }
}
