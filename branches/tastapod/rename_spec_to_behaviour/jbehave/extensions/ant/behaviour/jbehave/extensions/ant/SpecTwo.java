/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 *
 * For Use from JBehaveAntTaskSpec
 */
public class SpecTwo {
	public static boolean wasCalled;

	public SpecTwo() {
		wasCalled = false;
	}
	public void shouldDoBoo() {
		wasCalled = true;
	}
}
