/*
 * Created on 17-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;

import org.jmock.Mock;
import jbehave.framework.exception.VerificationException;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Jul-2004
 */
public class NULLMocker implements Mocker {
	public Mock mock(Class mockedType) {
		return null;
	}

	public void verifyMocks() throws VerificationException {

	}
}
