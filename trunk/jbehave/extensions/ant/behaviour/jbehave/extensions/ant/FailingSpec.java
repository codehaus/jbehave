/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant;

import jbehave.framework.Verify;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 *
 * For use from JBehaveAntTaskSpec
 */
public class FailingSpec {
	public void shouldFail() throws Exception {
		Verify.that(false);
	}
}
