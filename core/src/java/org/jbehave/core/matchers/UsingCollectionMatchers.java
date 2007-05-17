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
	
    public static CustomMatcher contains(final Matcher[] matchers) {
        if (matchers.length == 0) {
            return contains(UsingEqualityMatchers.nothing());
        }
        
        
        CustomMatcher matcher = containsA(matchers[0]);
        for (int i = 1; i < matchers.length; i++) {
            matcher = UsingLogicalMatchers.and(matcher, containsA(matchers[i]));
        }
        
        final CustomMatcher finalMatcher = matcher;
        
        return new CustomMatcher(""){
            public boolean matches(Object arg) {
                return finalMatcher.matches(arg);
            }

            public String describe(Object arg) {
                Collection collection = (Collection) arg;
                StringBuffer buffer = new StringBuffer();
                buffer.append("a " + arg.getClass().getSimpleName() + " containing ");
				describe(collection, buffer);               
                return buffer.toString();
            }

			private void describe(Collection collection, StringBuffer buffer) {
				buffer.append("[");
                for (Iterator iter = collection.iterator(); iter.hasNext();) {
                    buffer.append(iter.next());
                    if(iter.hasNext()) {
                        buffer.append(", ");
                    }                    
                }
                buffer.append("]");
			}
            
            public String toString() {
                StringBuffer buffer = new StringBuffer();
				describe(Arrays.asList(matchers), buffer);
				return "a collection containing " + buffer;
            }
        };
    }
    

    private static CustomMatcher containsA(final Matcher matcher) {
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


    public static CustomMatcher contains(Matcher matcher) {
        return contains(new Matcher[] {matcher});
    }


    public static CustomMatcher contains(Object object) {
        return contains(new Object[]{object});
    }


    public static CustomMatcher contains(Object a, Object b) {
        return contains(new Object[]{a, b});
    }

	public static CustomMatcher contains(Object a, Object b, Object c) {
		return contains(new Object[]{a, b, c});
	}

	public static CustomMatcher contains(Matcher a, Matcher b) {
		return contains(new Matcher[] {a, b});
	} 

	public static CustomMatcher contains(Matcher a, Matcher b, Matcher c) {
		return contains(new Matcher[] {a, b, c});
	}    
	
	public static CustomMatcher contains(Object[] objects) {
		return contains(allEq(objects));
	}
	
	public static CustomMatcher containsOnly(Object a) {
		return containsOnly(new Object[] {a});
	}
	
	public static CustomMatcher containsOnly(Object a, Object b) {
		return containsOnly(new Object[] {a, b});
	}
	
	public static CustomMatcher containsOnly(Object a, Object b, Object c) {
		return containsOnly(new Object[] {a, b, c});
	}
	
	public static CustomMatcher containsOnly(Object[] objects) {
		return containsOnly(allEq(objects));
	}

	public static CustomMatcher containsOnly(Matcher a) {
		return containsOnly(new Matcher[] {a});
	}
	
	public static CustomMatcher containsOnly(Matcher a, Matcher b) {
		return containsOnly(new Matcher[] {a, b});
	}
	
	public static CustomMatcher containsOnly(Matcher a, Matcher b, Matcher c) {
		return containsOnly(new Matcher[] {a, b, c});
	}
	
	public static CustomMatcher containsOnly(final Matcher[] matchers) {
		CustomMatcher lengthMatcher = new CustomMatcher("nothing else"){
			public boolean matches(Object arg) {
				return ((Collection)arg).size() == matchers.length;
			}};
			
		return contains(matchers).and(lengthMatcher);
	}

	private static Matcher[] allEq(Object[] objects) {
		Matcher[] matchers = new Matcher[objects.length];
		for (int i = 0; i < objects.length; i++) {
			matchers[i] = UsingEqualityMatchers.eq(objects[i]);
		}
		return matchers;
	}
	

}
