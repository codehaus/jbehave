package org.jbehave.core.matchers;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;

public abstract class CustomMatcher extends UsingMatchers.CustomMatcher implements Matcher {

    public CustomMatcher(String description) {
        super(description);
    }

}
