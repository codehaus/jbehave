/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;

import jbehave.core.Ensure;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 *
 * For use from {@link AntTaskBehaviour}
 */
public class FailingBehaviourClass {
	public void shouldFail() throws Exception {
		Ensure.that(false);
	}
}
