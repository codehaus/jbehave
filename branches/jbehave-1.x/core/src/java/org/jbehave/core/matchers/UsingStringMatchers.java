package org.jbehave.core.matchers;

import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingStringMatchers {

    public static CustomMatcher startsWith(final String fragment) {
        return new CustomMatcher("string starting with <" + fragment + ">") {
            public boolean matches(Object arg) {
                return ((String)arg).startsWith(fragment);
            }
        };
    }

    public static CustomMatcher endsWith(final String fragment) {
        return new CustomMatcher("string ending with <" + fragment + ">") {
            public boolean matches(Object arg) {
                return ((String)arg).endsWith(fragment);
            }
        };
    }

    public static CustomMatcher contains(final String fragment) {
        return new CustomMatcher("string containing <" + fragment + ">") {
            public boolean matches(Object arg) {
                return arg.toString().indexOf(fragment) != -1;
            }
        };
    }

}
