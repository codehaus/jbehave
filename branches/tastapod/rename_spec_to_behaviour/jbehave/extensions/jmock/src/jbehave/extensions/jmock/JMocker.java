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
import junit.framework.AssertionFailedError;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Jul-2004
 */
public class JMocker implements Mocker{
	private List mocks = new LinkedList();

	public Mock mock(Class mockedType) {
		Mock mock = new Mock(mockedType);
		mocks.add(mock);
		return mock;
	}

	public void verifyMocks() throws VerificationException {
    	try {
			for (Iterator iter = mocks.iterator(); iter.hasNext();) {
				Mock mock = (Mock) iter.next();
				mock.verify();
			}
		} catch (AssertionFailedError afe) {
			throw new VerificationException(afe.getMessage());
		}
	}
}
