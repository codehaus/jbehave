package org.jbehave.core.matchers;

import org.jbehave.core.Ensure;

public class UsingStringMatchersBehaviour {
    
    public void shouldProvideCommonStringMatchers() {
        
        Ensure.that("octopus", UsingStringMatchers.contains("top"));
        Ensure.that("octopus", UsingLogicalMatchers.not(UsingStringMatchers.contains("eight")));
        Ensure.that("octopus", UsingStringMatchers.startsWith("octo"));
        Ensure.that("octopus", UsingLogicalMatchers.not(UsingStringMatchers.startsWith("eight")));
        Ensure.that("octopus", UsingStringMatchers.endsWith("pus"));
        Ensure.that("octopus", UsingLogicalMatchers.not(UsingStringMatchers.endsWith("eight")));
    }
}
