/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant;

import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 *
 * For use from {@link AntTaskBehaviour}
 */
public class FailingBehaviourClass {
	public void shouldFail() throws Exception {
		Verify.that(false);
	}
}
