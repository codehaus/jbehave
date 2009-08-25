package org.jbehave.core.matchers;

import org.jbehave.core.Ensure;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingLogicalMatchersBehaviour {
    
    public void shouldProvideConditionalMatchers() throws Exception {
        String horse = "horse";
        String cow = "cow";
        
        Ensure.that(horse, UsingLogicalMatchers.or(eq(horse), eq(cow)));
        Ensure.that(cow, UsingLogicalMatchers.either(eq(horse), eq(cow)));
        
        Ensure.that(horse, UsingLogicalMatchers.and(eq(horse), UsingStringMatchers.contains("ors")));
        Ensure.that(cow, UsingLogicalMatchers.both(eq(cow), UsingStringMatchers.endsWith("ow")));        
    }
    
    public void shouldProvideNegativeMatcher() throws Exception {
    	Ensure.that("horse", UsingLogicalMatchers.not(eq("cow")));
    }

	public void shouldUseFirstFailingDelegateMatcherToDescribeArgs() {
    	CustomMatcher customizedDescription = new CustomMatcher("") {
					public boolean matches(Object arg) { return false; }
					public String describe(Object arg) { return "a customized description of " + arg; }
		    	};
		    	
		CustomMatcher both4And5 = UsingLogicalMatchers.and(eq(Integer.valueOf(4)), customizedDescription);
		Ensure.that(both4And5.describe(Integer.valueOf(4)),
    			eq("a customized description of 4"));

    	CustomMatcher either4Or5 = UsingLogicalMatchers.or(customizedDescription, eq(Integer.valueOf(4)));
		Ensure.that(either4Or5.describe(Integer.valueOf(4)),
    			eq("a customized description of 4"));	
		
		Ensure.that(UsingLogicalMatchers.not(customizedDescription).describe(Integer.valueOf(4)), eq("a customized description of 4"));
	}
	
	private Matcher eq(Object value) {
		return UsingEqualityMatchers.eq(value);
	}
}
