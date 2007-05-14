package org.jbehave.core.matchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingCollectionMatchers {

    public static CustomMatcher isContainedIn(final Collection collection) {
        return new CustomMatcher("is contained in " + collection) {
            public boolean matches(Object arg) {
                return collection.contains(arg);
            }
        };
    }
	
    public static CustomMatcher collectionContaining(final Matcher[] matchers) {
        if (matchers.length == 0) {
            return collectionContaining(UsingEqualityMatchers.nothing());
        }
        
        
        CustomMatcher matcher = collectionContainingA(matchers[0]);
        for (int i = 1; i < matchers.length; i++) {
            matcher = UsingLogicalMatchers.and(matcher, collectionContainingA(matchers[i]));
        }
        
        final CustomMatcher finalMatcher = matcher;
        
        return new CustomMatcher(""){
            public boolean matches(Object arg) {
                return finalMatcher.matches(arg);
            }

            public String describe(Object arg) {
                Collection collection = (Collection) arg;
                StringBuffer buffer = describe(collection);
                return buffer.toString();
            }

			private StringBuffer describe(Collection collection) {
				StringBuffer buffer = new StringBuffer().append("[");
                for (Iterator iter = collection.iterator(); iter.hasNext();) {
                    buffer.append(iter.next());
                    if(iter.hasNext()) {
                        buffer.append(", ");
                    }                    
                }
                buffer.append("]");
				return buffer;
			}
            
            public String toString() {
                return "a collection containing " + describe(Arrays.asList(matchers));
            }
        };
    }
    

    private static CustomMatcher collectionContainingA(final Matcher matcher) {
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


    public static CustomMatcher collectionContaining(Matcher matcher) {
        return collectionContaining(new Matcher[] {matcher});
    }


    public static CustomMatcher collectionContaining(Object object) {
        return collectionContaining(new Object[]{object});
    }


    public static CustomMatcher collectionContaining(Object a, Object b) {
        return collectionContaining(new Object[]{a, b});
    }

	public static CustomMatcher collectionContaining(Object a, Object b, Object c) {
		return collectionContaining(new Object[]{a, b, c});
	}

	public static CustomMatcher collectionContaining(Matcher a, Matcher b) {
		return collectionContaining(new Matcher[] {a, b});
	} 

	public static CustomMatcher collectionContaining(Matcher a, Matcher b, Matcher c) {
		return collectionContaining(new Matcher[] {a, b, c});
	}    
	
	public static CustomMatcher collectionContaining(Object[] objects) {
		Matcher[] matchers = new Matcher[objects.length];
		for (int i = 0; i < objects.length; i++) {
			matchers[i] = UsingEqualityMatchers.eq(objects[i]);
		}
		return collectionContaining(matchers);
	}
	
	public static CustomMatcher collectionContainingOnly(final Matcher[] matchers) {
		CustomMatcher lengthMatcher = new CustomMatcher("nothing else"){
			public boolean matches(Object arg) {
				return ((Collection)arg).size() == matchers.length;
			}};
			
		return collectionContaining(matchers).and(lengthMatcher);
	}

}
