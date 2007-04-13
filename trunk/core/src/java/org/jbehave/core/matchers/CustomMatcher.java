package org.jbehave.core.matchers;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;

public abstract class CustomMatcher extends UsingMatchers implements Matcher {
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
    
    public String describe(Object arg) {
        return "" + arg;
    }
}
