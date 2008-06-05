package org.jbehave.core.matchers;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingLogicalMatchers {

    public static CustomMatcher and(final Matcher a, final Matcher b) {
        return new DoubleMatcherMatcher(a + " and " + b, a, b) {
            public boolean matches(Object arg) {
                return a.matches(arg) && b.matches(arg);
            }
            

        };
    }

    public static CustomMatcher both(Matcher a, Matcher b) {
        return and(a, b);
    }

    public static CustomMatcher or(final Matcher a, final Matcher b) {
        return new DoubleMatcherMatcher(a + " or " + b, a, b) {
            public boolean matches(Object arg) {
                return a.matches(arg) || b.matches(arg);
            }
        };
    }

    public static CustomMatcher either(Matcher a, Matcher b) {
        return or(a, b);
    }    

    public static CustomMatcher not(final Matcher c) {
        return new CustomMatcher("not " + c) {
            public boolean matches(Object arg) {
                return !c.matches(arg);
            }
            
            public String describe(Object arg) {
            	if (c instanceof CustomMatcher) {
            		return ((CustomMatcher)c).describe(arg);
            	}
            	return super.describe(arg);
            }
        };
    }

    private static abstract class DoubleMatcherMatcher extends CustomMatcher {
		private final Matcher a;
		private final Matcher b;

		public DoubleMatcherMatcher(String description, Matcher a, Matcher b) {
			super(description);
			this.a = a;
			this.b = b; 
		}
    	
        public String describe(Object arg) {            	
        	if (!a.matches(arg) && a instanceof CustomMatcher) {
    			return ((CustomMatcher)a).describe(arg);
        	} else if (!b.matches(arg) && b instanceof CustomMatcher) {
        		return ((CustomMatcher)b).describe(arg);
        	}
        	return super.describe(arg);
        }
    }
}
