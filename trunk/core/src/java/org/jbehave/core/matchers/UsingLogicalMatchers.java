package org.jbehave.core.matchers;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingLogicalMatchers {

    public static Matcher not(final CustomMatcher matcher) {
        return new CustomMatcher("not (" + matcher + ")") {
            public boolean matches(Object arg) {
                return !matcher.matches(arg);
            }
        };
    }

    public static CustomMatcher and(final Matcher a, final Matcher b) {
        return new CustomMatcher("(" + a + " and " + b + ")") {
            public boolean matches(Object arg) {
                return a.matches(arg) && b.matches(arg);
            }
        };
    }

    public static CustomMatcher both(Matcher a, Matcher b) {
        return and(a, b);
    }

    public static CustomMatcher or(final Matcher a, final Matcher b) {
        return new CustomMatcher("(" + a + " or " + b + ")") {
            public boolean matches(Object arg) {
                return a.matches(arg) || b.matches(arg);
            }
        };
    }

    public static CustomMatcher either(Matcher a, Matcher b) {
        return or(a, b);
    }    

    public static CustomMatcher not(final Matcher c) {
        return new CustomMatcher("not (" + c + ")") {
            public boolean matches(Object arg) {
                return !c.matches(arg);
            }
        };
    }


}
