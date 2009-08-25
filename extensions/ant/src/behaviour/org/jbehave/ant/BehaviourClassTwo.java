/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.ant;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 *
 * For Use from JBehaveAntTaskSpec
 */
public class BehaviourClassTwo {
	public static boolean wasCalled;

	public BehaviourClassTwo() {
		wasCalled = false;
	}
	public void shouldDoBoo() {
		wasCalled = true;
	}
}
