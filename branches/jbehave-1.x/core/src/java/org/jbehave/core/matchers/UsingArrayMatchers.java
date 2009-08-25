package org.jbehave.core.matchers;

import java.util.Arrays;

import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers.CustomMatcher;

public class UsingArrayMatchers {

    public static CustomMatcher isContainedIn(final Object[] array) {
        return new CustomMatcher("is contained in " + array) {
            public boolean matches(Object arg) {
                return Arrays.asList(array).contains(arg);
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
        
        return suitablyDescriptiveMatcher(matchers, matcher);
    }

    private static CustomMatcher suitablyDescriptiveMatcher(final Matcher[] matchers, final CustomMatcher finalMatcher) {
        return new CustomMatcher(""){
            public boolean matches(Object arg) {
                return finalMatcher.matches(arg);
            }

            public String describe(Object arg) {
                Object[] array = (Object[]) arg;
                StringBuffer buffer = new StringBuffer();
                buffer.append("a " + arg.getClass().getName() + " containing ");
                describe(array, buffer);               
                return buffer.toString();
            }

            private void describe(Object[] array, StringBuffer buffer) {
                buffer.append("[");
                for (int i = 0; i < array.length; i++) {
                    buffer.append(array[i]);
                    if(i < array.length - 1) {
                        buffer.append(", ");
                    }                    
                }
                buffer.append("]");
            }
            
            public String toString() {
                StringBuffer buffer = new StringBuffer();
                describe(matchers, buffer);
                return "an array containing " + buffer;
            }
        };
    }
    

    private static CustomMatcher containsA(final Matcher matcher) {
        return new CustomMatcher("" + matcher) {
            public boolean matches(Object arg) {
                Object[] array = (Object[]) arg;
                for (int i = 0; i < array.length; i++) {
                    if (matcher.matches(array[i])) {
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
                return ((Object[])arg).length == matchers.length;
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
    
    public static CustomMatcher containsInOrder(Object a, Object b) {
        return containsInOrder(new Object[] {a, b});
    }
    
    public static CustomMatcher containsInOrder(Object a, Object b, Object c) {
        return containsInOrder(new Object[] {a, b, c});
    }
    
    public static CustomMatcher containsInOrder(Object[] objects) {
        return containsInOrder(allEq(objects));
    }

    public static CustomMatcher containsInOrder(Matcher a, Matcher b) {
        return containsInOrder(new Matcher[] {a, b});
    }
    
    public static CustomMatcher containsInOrder(Matcher a, Matcher b, Matcher c) {
        return containsInOrder(new Matcher[] {a, b, c});
    }
    
    public static CustomMatcher containsInOrder(final Matcher[] matchers) {
        if (matchers.length == 0) {
            return contains(UsingEqualityMatchers.nothing());
        }
                
        CustomMatcher aggregateMatcher = containsAt(matchers[0], 0);
        
        for (int i = 1; i < matchers.length; i++) {
            aggregateMatcher = UsingLogicalMatchers.and(aggregateMatcher, containsAt(matchers[i], i));
        }
        
        Matcher inOrderDescription = new Matcher() {
            public boolean matches(Object arg) { return true; }
            public String toString() { return "in order"; }
        };
        
        return suitablyDescriptiveMatcher(matchers, aggregateMatcher).and(inOrderDescription);
    }

    private static CustomMatcher containsAt(final Matcher matcher, final int i) {
        return new CustomMatcher("" + matcher) {
            public boolean matches(Object arg) {
                Object[] array = (Object[]) arg;
                return matcher.matches(array[i]);
            }
        };
    }   
}
