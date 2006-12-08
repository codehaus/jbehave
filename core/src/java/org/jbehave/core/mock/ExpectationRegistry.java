package org.jbehave.core.mock;

public interface ExpectationRegistry {
    Expectation lookup(String id);
}