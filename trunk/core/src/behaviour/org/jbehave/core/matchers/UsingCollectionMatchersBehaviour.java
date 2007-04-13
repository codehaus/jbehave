package org.jbehave.core.matchers;

import java.util.ArrayList;

import org.jbehave.core.Ensure;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.UsingMatchers;

public class UsingCollectionMatchersBehaviour {
    private static final String NL = System.getProperty("line.separator");

    public void shouldProvideMatchersForCollectionsContainingAThing() {
        ArrayList list = new ArrayList();
        list.add(new Integer(3));
        
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(new Integer(3)));
        Ensure.that(list, UsingCollectionMatchers.collectionContaining(UsingEqualityMatchers.eq(new Integer(3))));
        
        Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.collectionContaining(new Integer(5))));
        Ensure.that(list, UsingLogicalMatchers.not(UsingCollectionMatchers.collectionContaining(UsingEqualityMatchers.eq(new Integer(5)))));
    }
    
    public void shouldDescribeMatchersForCollections() {
        UsingMatchers m = new UsingMatchers() {};
        
        ArrayList list = new ArrayList();
        list.add(new Integer(5));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.collectionContaining(new Integer(3)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Expected: " + NL + "a collection containing [equal to <3>]" + NL + "but got: " + NL + "[5]"));
        }
        
        list.add(new Integer(6));
        
        try {
            Ensure.that(list, UsingCollectionMatchers.collectionContaining(new Integer(4), new Integer(5)));
        } catch (VerificationException e) {
            Ensure.that(e.getMessage(), UsingEqualityMatchers.eq("Expected: " + NL + "a collection containing [equal to <4>, equal to <5>]" + NL + "but got: " + NL + "[5, 6]"));
        }
    }
}
