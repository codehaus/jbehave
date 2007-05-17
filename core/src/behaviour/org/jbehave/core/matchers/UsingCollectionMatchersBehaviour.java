package org.jbehave.core.matchers;

import java.util.ArrayList;

import org.jbehave.core.Ensure;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;

public class UsingCollectionMatchersBehaviour {
    private static final String NL = System.getProperty("line.separator");

    private Matcher eq(Integer value) {
    	return UsingEqualityMatchers.eq(value);
	}

	private Matcher not(Matcher matcher) {
		return UsingLogicalMatchers.not(matcher);
	}
    
    public void shouldProvideMatchersForCollectionsContainingAThing() {
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(3));
        
        Ensure.that(list, UsingCollectionMatchers.contains(Integer.valueOf(3)));
        Ensure.that(list, UsingCollectionMatchers.contains(eq(Integer.valueOf(3))));
        
        Ensure.that(list, not(UsingCollectionMatchers.contains(Integer.valueOf(5))));
        Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.contains(eq(Integer.valueOf(5)))));
    }

	public void shouldProvideMatchersForCollectionsContainingSomeThings() {
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(3));
        list.add(Integer.valueOf(4));
        list.add(Integer.valueOf(5));
    	
    	Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.contains(Integer.valueOf(7))));
    	Ensure.that(list, UsingCollectionMatchers.contains(Integer.valueOf(3), Integer.valueOf(4)));
    	Ensure.that(list, UsingCollectionMatchers.contains(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3)));
        Ensure.that(list, UsingCollectionMatchers.contains(new Object[] {Integer.valueOf(3)}));
       
    	Ensure.that(list, not(UsingCollectionMatchers.contains(eq(Integer.valueOf(7)))));
    	Ensure.that(list, UsingCollectionMatchers.contains(eq(Integer.valueOf(3)), eq(Integer.valueOf(4))));
    	Ensure.that(list, UsingCollectionMatchers.contains(eq(Integer.valueOf(5)), eq(Integer.valueOf(4)), eq(Integer.valueOf(3))));
        Ensure.that(list, UsingCollectionMatchers.contains(new Matcher[] {eq(Integer.valueOf(3))}));
    }
	
	public void shouldProvideMatchersForCollectionsContainingOnlyThings() {
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(3));
        list.add(Integer.valueOf(4));
        list.add(Integer.valueOf(5));
    	
    	Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.containsOnly(Integer.valueOf(3), Integer.valueOf(4))));
    	Ensure.that(list, UsingCollectionMatchers.containsOnly(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3)));
    	Ensure.that(list, UsingCollectionMatchers.containsOnly(eq(Integer.valueOf(5)), eq(Integer.valueOf(4)), eq(Integer.valueOf(3))));
	}
    
    public void shouldDescribeMatchersForCollections() {
        UsingMatchers m = new UsingMatchers() {};
        
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(5));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.contains(Integer.valueOf(3)));
            UsingExceptions.fail("Didn't throw exception");
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq(
            		"Expected: " + NL + 
            		"a collection containing [equal to <3>]" + NL + 
            		"but got: " + NL + 
            		"a ArrayList containing [5]"));
        }
        
        list.add(Integer.valueOf(6));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.contains(Integer.valueOf(4), Integer.valueOf(5)));
            UsingExceptions.fail("Didn't throw exception");
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq(
            		"Expected: " + NL + 
            		"a collection containing [equal to <4>, equal to <5>]" + NL + 
            		"but got: " + NL + 
            		"a ArrayList containing [5, 6]"));
        }
    }
}
