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
 * This is purely for using from JBehaveAntTaskSpec
 */
public class SpecOne {
	public static boolean wasCalled;

	public SpecOne() {
		wasCalled = false;
	}

	public void shouldDoBlah() throws Exception {
		wasCalled = true;	
	}
}
