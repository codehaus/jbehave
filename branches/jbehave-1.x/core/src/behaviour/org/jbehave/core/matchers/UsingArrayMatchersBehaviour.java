package org.jbehave.core.matchers;

import org.jbehave.core.Ensure;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingArrayMatchersBehaviour {

    private Matcher eq(Object value) {
    	return UsingEqualityMatchers.eq(value);
	}

	private Matcher not(Matcher matcher) {
		return UsingLogicalMatchers.not(matcher);
	}
    
    public void shouldProvideMatchersForCollectionsContainingAThing() {
        Integer[] array = new Integer[] { Integer.valueOf(3) };
        
        Ensure.that(array, UsingArrayMatchers.contains(Integer.valueOf(3)));
        Ensure.that(array, UsingArrayMatchers.contains(eq(Integer.valueOf(3))));
        
        Ensure.that(array, not(UsingArrayMatchers.contains(Integer.valueOf(5))));
        Ensure.that(array, UsingLogicalMatchers.not(UsingArrayMatchers.contains(eq(Integer.valueOf(5)))));
    }

	public void shouldProvideMatchersForCollectionsContainingSomeThings() {
        Integer[] array = new Integer[] {
                Integer.valueOf(3),
                Integer.valueOf(4),
                Integer.valueOf(5)};
    	
    	Ensure.that(array, UsingLogicalMatchers.not(UsingArrayMatchers.contains(Integer.valueOf(7))));
    	Ensure.that(array, UsingArrayMatchers.contains(Integer.valueOf(3), Integer.valueOf(4)));
    	Ensure.that(array, UsingArrayMatchers.contains(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3)));
        Ensure.that(array, UsingArrayMatchers.contains(new Object[] {Integer.valueOf(3)}));
       
    	Ensure.that(array, not(UsingArrayMatchers.contains(eq(Integer.valueOf(7)))));
    	Ensure.that(array, UsingArrayMatchers.contains(eq(Integer.valueOf(3)), eq(Integer.valueOf(4))));
    	Ensure.that(array, UsingArrayMatchers.contains(eq(Integer.valueOf(5)), eq(Integer.valueOf(4)), eq(Integer.valueOf(3))));
        Ensure.that(array, UsingArrayMatchers.contains(new Matcher[] {eq(Integer.valueOf(3))}));
    }
	
	public void shouldProvideMatchersForCollectionsContainingOnlyThings() {
        Integer[] array = new Integer[] {
                Integer.valueOf(3),
                Integer.valueOf(4),
                Integer.valueOf(5)};
    	
    	Ensure.that(array, UsingLogicalMatchers.not(UsingArrayMatchers.containsOnly(Integer.valueOf(3), Integer.valueOf(4))));
    	Ensure.that(array, UsingArrayMatchers.containsOnly(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3)));
    	Ensure.that(array, UsingArrayMatchers.containsOnly(eq(Integer.valueOf(5)), eq(Integer.valueOf(4)), eq(Integer.valueOf(3))));
	}
	
	public void shouldProvideMatchersForCollectionsContainingThingsInOrder() {
        Integer[] array = new Integer[] {
                Integer.valueOf(3),
                Integer.valueOf(4),
                Integer.valueOf(5)};
    	
    	Ensure.that(array, not(UsingArrayMatchers.containsInOrder(Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(4))));
    	Ensure.that(array, UsingArrayMatchers.containsInOrder(Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)));
	}
    
    public void shouldDescribeMatchersForCollections() {
        Integer[] array = new Integer[] { Integer.valueOf(5), Integer.valueOf(6)};
        
        CustomMatcher contains = UsingArrayMatchers.contains(Integer.valueOf(4), Integer.valueOf(5));
        CustomMatcher containsOnly = UsingArrayMatchers.containsOnly(Integer.valueOf(4), Integer.valueOf(5));
        CustomMatcher containsInOrder = UsingArrayMatchers.containsInOrder(Integer.valueOf(4), Integer.valueOf(5));
        
        // Describe what we expected
        Ensure.that(contains.toString(), eq("an array containing [equal to <4>, equal to <5>]"));
        Ensure.that(containsOnly.toString(), eq("an array containing [equal to <4>, equal to <5>] and nothing else"));
        Ensure.that(containsInOrder.toString(), eq("an array containing [equal to <4>, equal to <5>] and in order"));
        
        // Describe what we got
		Ensure.that(contains.describe(array), eq("a " + Integer[].class.getName()  + " containing [5, 6]"));
		Ensure.that(containsOnly.describe(array), eq("a " + Integer[].class.getName()  + " containing [5, 6]"));
		Ensure.that(containsInOrder.describe(array), eq("a " + Integer[].class.getName()  + " containing [5, 6]"));
    }
}
