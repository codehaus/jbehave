package org.jbehave.core.matchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingCollectionMatchers {

    public static CustomMatcher collectionContaining(final CustomMatcher[] matchers) {
        if (matchers.length == 0) {
            return collectionContaining(UsingEqualityMatchers.nothing());
        }
        
        
        CustomMatcher matcher = collectionContainingA(matchers[0]);
        for (int i = 1; i < matchers.length; i++) {
            matcher = matchers[i].and(collectionContainingA(matcher));
        }
        
        final CustomMatcher finalMatcher = matcher;
        
        return new CustomMatcher(""){
            public boolean matches(Object arg) {
                return finalMatcher.matches(arg);
            }

            public String describe(Object arg) {
                Collection collection = (Collection) arg;
                StringBuffer buffer = new StringBuffer().append("[");
                for (Iterator iter = collection.iterator(); iter.hasNext();) {
                    buffer.append(iter.next());
                    if(iter.hasNext()) {
                        buffer.append(", ");
                    }                    
                }
                buffer.append("]");
                return buffer.toString();
            }
            
            public String toString() {
                return "a collection containing " + describe(Arrays.asList(matchers));
            }
        };
    }
    

    private static CustomMatcher collectionContainingA(final CustomMatcher matcher) {
        return new CustomMatcher("" + matcher) {
            public boolean matches(Object arg) {
                Collection collection = (Collection) arg;
                for (Iterator iter = collection.iterator(); iter.hasNext();) {
                    if (matcher.matches(iter.next())) {
                        return true;
                    }
                }
                return false;
            }
        };
    }


    public static CustomMatcher collectionContaining(CustomMatcher matcher) {
        return collectionContaining(new CustomMatcher[] {matcher});
    }


    public static CustomMatcher collectionContaining(Object object) {
        return collectionContaining(UsingEqualityMatchers.eq(object));
    }


    public static CustomMatcher collectionContaining(Object object1, Object object2) {
        return collectionContaining(new CustomMatcher[] {UsingEqualityMatchers.eq(object1), UsingEqualityMatchers.eq(object2)});
    }


    public static CustomMatcher isContainedIn(final Collection collection) {
        return new CustomMatcher("is contained in " + collection) {
            public boolean matches(Object arg) {
                return collection.contains(arg);
            }
        };
    }    

}
