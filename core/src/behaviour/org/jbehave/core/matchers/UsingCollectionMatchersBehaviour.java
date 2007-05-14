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
        
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(Integer.valueOf(3)));
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(eq(Integer.valueOf(3))));
        
        Ensure.that(list, not(UsingCollectionMatchers.collectionContaining(Integer.valueOf(5))));
        Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.collectionContaining(eq(Integer.valueOf(5)))));
    }

	public void shouldProvideMatchersForCollectionsContainingSomeThings() {
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(3));
        list.add(Integer.valueOf(4));
        list.add(Integer.valueOf(5));
    	
    	Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.collectionContaining(Integer.valueOf(7))));
    	Ensure.that(list, UsingCollectionMatchers.collectionContaining(Integer.valueOf(3), Integer.valueOf(4)));
    	Ensure.that(list, UsingCollectionMatchers.collectionContaining(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3)));
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(new Object[] {Integer.valueOf(3)}));
       
    	Ensure.that(list, not(UsingCollectionMatchers.collectionContaining(eq(Integer.valueOf(7)))));
    	Ensure.that(list, UsingCollectionMatchers.collectionContaining(eq(Integer.valueOf(3)), eq(Integer.valueOf(4))));
    	Ensure.that(list, UsingCollectionMatchers.collectionContaining(eq(Integer.valueOf(5)), eq(Integer.valueOf(4)), eq(Integer.valueOf(3))));
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(new Matcher[] {eq(Integer.valueOf(3))}));
    }
    
    public void shouldDescribeMatchersForCollections() {
        UsingMatchers m = new UsingMatchers() {};
        
        ArrayList list = new ArrayList();
        list.add(Integer.valueOf(5));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.collectionContaining(Integer.valueOf(3)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Expected: " + NL + "a collection containing [equal to <3>]" + NL + "but got: " + NL + "[5]"));
        }
        
        list.add(Integer.valueOf(6));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.collectionContaining(Integer.valueOf(4), Integer.valueOf(5)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Expected: " + NL + "a collection containing [equal to <4>, equal to <5>]" + NL + "but got: " + NL + "[5, 6]"));
        }
    }
}
