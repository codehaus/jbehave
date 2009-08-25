package org.jbehave.core.matchers;

import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingEqualityMatchers {

    public static CustomMatcher isNotNull() {
        return new CustomMatcher("object not null") {
            public boolean matches(Object arg) {
                return arg != null;
            }
        };
    }

    public static CustomMatcher isNull() {
        return new CustomMatcher("object is null") {
            public boolean matches(Object arg) {
                return arg == null;
            }
        };
    }

    public static CustomMatcher eq(final Object expectedArg) {
        return new CustomMatcher("equal to <" + expectedArg + ">") {
            public boolean matches(Object arg) {
                return arg == null ? expectedArg == null : arg.equals(expectedArg);
            }
        };
    }

    public static CustomMatcher eq(final double expectedArg, final double delta) {
        return new CustomMatcher("floating point number equal to " + expectedArg) {
            public boolean matches(Object arg) {
                double value = ((Number) arg).doubleValue();
                return Math.abs(expectedArg - value) <= delta;
            }
        };
    }

    public static CustomMatcher eq(double expectedArg) {
        return eq(expectedArg, 0.0);
    }
    
    public static CustomMatcher eq(final long expectedArg) {
        return new CustomMatcher("integer type equal to " + expectedArg) {
            public boolean matches(Object arg) {
                Number n = (Number)arg;
                return n.longValue() == expectedArg;
            }          
        };
    }
    
    public static CustomMatcher eq(final char expectedArg) {
        return new CustomMatcher("character equal to '" + expectedArg + "'") {
            public boolean matches(Object arg) {
                Character n = (Character)arg;
                return n.charValue() == expectedArg;
            }          
        };        
    }

    public static CustomMatcher eq(final boolean expectedArg) {
        return new CustomMatcher("boolean " + expectedArg) {
            public boolean matches(Object arg) {
                Boolean n = (Boolean)arg;
                return n.booleanValue() == expectedArg;
            }          
        };
    }

    public static CustomMatcher is(Object expectedArg) {
        return sameInstanceAs(expectedArg);
    }

    public static CustomMatcher sameInstanceAs(final Object expectedArg) {
        return new CustomMatcher("same instance as <" + expectedArg + ">") {
            public boolean matches(Object arg) {
                return expectedArg == arg;
            }
        };
    }

    public static CustomMatcher anything() {
        return new CustomMatcher("anything") {
            public boolean matches(Object arg) {
                return true;
            }
        };
    }

    public static CustomMatcher nothing() {
        return new CustomMatcher("nothing") {
            public boolean matches(Object arg) {
                return false;
            }
        };
    }

    public static CustomMatcher a(Class type) {
        return isA(type);
    }

    public static CustomMatcher isA(final Class type) {
        return new CustomMatcher("object of type " + type.getName()) {
            public boolean matches(Object arg) {
                return type.isInstance(arg);
            }
        };
    }

}
