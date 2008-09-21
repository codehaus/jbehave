package org.jbehave.util;

import org.hamcrest.Matcher;
import org.junit.Assert;

/**
 * Collection of static utility ensure methods that use Hamcrest matchers with JUnit 4.
 * 
 * @author Elizabeth Keogh
 */
public class JUnit4Ensure {

    public static void ensureThat(boolean matches) {
        Assert.assertTrue(matches);
    }
    
    public static <T> void ensureThat(T actual, Matcher<T> matcher) {
        Assert.assertThat(actual, matcher);
    }

    public static boolean not(boolean matches) {
        return !matches;
    }

}
