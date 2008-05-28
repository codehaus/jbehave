package org.jbehave;

import org.hamcrest.Matcher;
import org.junit.Assert;

public class Ensure {

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
