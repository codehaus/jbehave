package jbehave.core.mock;

import java.util.Collection;

import jbehave.core.Block;
import jbehave.core.exception.PendingException;
import jbehave.core.exception.VerificationException;

/**
 * Support for constraint-based verification
 */
public abstract class UsingConstraints {
	public abstract static class CustomConstraint extends UsingConstraints implements Constraint {
		private final String description;

		public CustomConstraint(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
		
		public CustomConstraint and(Constraint that) {
			return and(this, that);
		}
		public CustomConstraint or(Constraint that) {
			return or(this, that);
		}
	}
	
	/** ensures object equals expected value */
	public CustomConstraint eq(final Object expectedArg) {
	    return new CustomConstraint("equal to <" + expectedArg + ">") {
	        public boolean matches(Object arg) {
	            return arg == null ? expectedArg == null : arg.equals(expectedArg);
	        }
	    };
	}

	/** eq(primitive) for float and double */
	public CustomConstraint eq(final double expectedArg, final double delta) {
	    return new CustomConstraint("floating point number equal to " + expectedArg) {
	        public boolean matches(Object arg) {
	            double value = ((Number) arg).doubleValue();
				return Math.abs(expectedArg - value) <= delta;
	        }
	    };
	}

	/** eq(primitive) for float and double */
	public CustomConstraint eq(final double expectedArg) {
		return eq(expectedArg, 0.0);
	}
	
	/** eq(primitive) for byte, short, integer and long */
	public CustomConstraint eq(final long expectedArg) {
	    return new CustomConstraint("integer type equal to " + expectedArg) {
	        public boolean matches(Object arg) {
	            Number n = (Number)arg;
	            return n.longValue() == expectedArg;
	        }          
	    };
	}

	/** eq(primitive) for char - note {@link Character} is not a {@link Number} */
	public CustomConstraint eq(final char expectedArg) {
	    return new CustomConstraint("character equal to '" + expectedArg + "'") {
	        public boolean matches(Object arg) {
	            Character n = (Character)arg;
	            return n.charValue() == expectedArg;
	        }          
	    };
	}

	/** eq(primitive) for boolean */
	public CustomConstraint eq(final boolean expectedArg) {
	    return new CustomConstraint("boolean " + expectedArg) {
	        public boolean matches(Object arg) {
	            Boolean n = (Boolean)arg;
	            return n.booleanValue() == expectedArg;
	        }          
	    };
	}

	public CustomConstraint sameInstanceAs(final Object expectedArg) {
	    return new CustomConstraint("same instance as <" + expectedArg.toString() + ">") {
	        public boolean matches(Object arg) {
	            return expectedArg == arg;
	        }
	    };
	}

	public CustomConstraint anything() {
	    return new CustomConstraint("anything") {
	        public boolean matches(Object arg) {
	            return true;
	        }
	    };
	}

	public CustomConstraint a(final Class type) {
	    return isA(type);
	}

	public CustomConstraint isA(final Class type) {
	    return new CustomConstraint("object of type " + type.getName()) {
	        public boolean matches(Object arg) {
	            return type.isInstance(arg);
	        }
	    };
	}
	
	public CustomConstraint startsWith(final String fragment) {
	    return new CustomConstraint("string starting with <" + fragment + ">") {
	        public boolean matches(Object arg) {
	            return ((String)arg).startsWith(fragment);
	        }
	    };
	}
	
	public CustomConstraint endsWith(final String fragment) {
		return new CustomConstraint("string ending with <" + fragment + ">") {
			public boolean matches(Object arg) {
				return ((String)arg).endsWith(fragment);
			}
		};
	}
	
	public CustomConstraint contains(final String fragment) {
		return new CustomConstraint("string ending with <" + fragment + ">") {
			public boolean matches(Object arg) {
				return ((String)arg).indexOf(fragment) != -1;
			}
		};
	}

	public CustomConstraint and(final Constraint a, final Constraint b) {
	    return new CustomConstraint("(" + a + " and " + b + ")") {
	        public boolean matches(Object arg) {
	            return a.matches(arg) && b.matches(arg);
	        }
	    };
	}

	public CustomConstraint both(final Constraint a, final Constraint b) {
	    return and(a, b);
	}

	public CustomConstraint or(final Constraint a, final Constraint b) {
	    return new CustomConstraint("(" + a + " or " + b + ")") {
	        public boolean matches(Object arg) {
	            return a.matches(arg) || b.matches(arg);
	        }
	    };
	}

	public CustomConstraint either(final Constraint a, final Constraint b) {
	    return either(a, b);
	}

	public CustomConstraint not(final Constraint c) {
	    return new CustomConstraint("not (" + c + ")") {
	        public boolean matches(Object arg) {
	            return !c.matches(arg);
	        }
	    };
	}
	
    public CustomConstraint isContainedIn(final Collection collection) {
        return new CustomConstraint("is contained in " + collection) {
            public boolean matches(Object arg) {
                return collection.contains(arg);
            }
        };
    }    
    public void ensureThat(Object arg, Constraint constraint, String message) {
    	if (!constraint.matches(arg)) {
    		fail("\nExpected: " +
    				(message != null ? "[" + message + "] " : "") +
    				constraint +
    				"\nbut got:  <" + arg + ">");
    	}
	}
    
	public void ensureThat(Object arg, Constraint constraint) {
		ensureThat(arg, constraint, null);
	}
	
	public void ensureThat(long arg, Constraint constraint, String message) {
		ensureThat(new Long(arg), constraint, message);
	}
	public void ensureThat(long arg, Constraint constraint) {
		ensureThat(new Long(arg), constraint, null);
	}
    
    public void ensureThat(double arg, Constraint constraint, String message) {
    	ensureThat(new Double(arg), constraint, message);
    }
    public void ensureThat(double arg, Constraint constraint) {
    	ensureThat(arg, constraint, null);
    }
    
    public void ensureThat(char arg, Constraint constraint, String message) {
    	ensureThat(new Character(arg), constraint, message);
    }
    public void ensureThat(char arg, Constraint constraint) {
    	ensureThat(arg, constraint, null);
    }
    
    public void ensureThat(boolean arg, Constraint constraint, String message) {
    	ensureThat(Boolean.valueOf(arg), constraint, message);
    }
    public void ensureThat(boolean arg, Constraint constraint) {
    	ensureThat(arg, constraint, null);
    }
    
    public void ensureThrows(Class exceptionType, Block block) throws Exception {
        try {
            block.run();
            fail("should have thrown " + exceptionType.getName());
        }
        catch (Exception e) {
            if (!exceptionType.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }
    }
    
    /** ensure(...) without constraints */
    public void ensureThat(boolean condition, String message) {
    	if (!condition) {
    		fail(message + ": expected condition was not met");
    	}
    }

    /** ensure(...) without constraints */
    public void ensureThat(boolean condition) {
        if (!condition) {
        	fail("Expected condition was not met");
        }
    }

    public void fail(String message) {
        throw new VerificationException(message);
    }

    public void fail(String message, Object expected, Object actual) {
        throw new VerificationException(message, expected, actual);
    }

	public void todo(String message) {
		throw new PendingException(message);
	}
	
	public void todo() {
		todo("TODO");
	}
}
