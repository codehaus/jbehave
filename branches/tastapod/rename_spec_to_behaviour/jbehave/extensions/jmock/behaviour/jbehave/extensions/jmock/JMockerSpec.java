/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;

import org.jmock.Mock;
import org.jmock.core.matcher.InvokeOnceMatcher;
import jbehave.framework.Verify;
import jbehave.framework.exception.VerificationException;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class JMockerSpec {

	interface ToMock {
		void doStuff();
		void doOtherStuff();
	}

	public void shouldCreateMocks() throws Exception {
		// setup
		Mocker mocker = new JMocker();

		// execute
		Mock m = mocker.mock(ToMock.class);

		// verify
		Verify.notNull(m);
		Verify.that(m.proxy() instanceof ToMock);
	}

	public void shouldVerifyMock() throws Exception {
		// setup
		Mocker mocker = new JMocker();
		Mock mockOne = mocker.mock(ToMock.class);
		mockOne.expects(new InvokeOnceMatcher()).method("doStuff");

		// execute
		try {
			mocker.verifyMocks();
			Verify.impossible("should of thrown verification exception");
		} catch (VerificationException ve) {
			containsString(ve.toString(), "expected once");
		}
	}

	public void shouldVerifyAllMocks() throws Exception {
		// setup
		Mocker mocker = new JMocker();
	    Mock mockOne = mocker.mock(ToMock.class);
		Mock mockTwo = mocker.mock(ToMock.class);

		mockOne.expects(new InvokeOnceMatcher()).method("doStuff");
		mockTwo.expects(new InvokeOnceMatcher()).method("doOtherStuff");

		// invoke on mockOne to show that mockTwo gets verified
		((ToMock)mockOne.proxy()).doStuff();
		try {
			mocker.verifyMocks();
			Verify.impossible("should of thrown VerificationException");
		} catch (VerificationException ve) {
			containsString(ve.toString(), "expected once: doOtherStuff");
		}

		mockOne.reset();
		mockTwo.reset();

		mockOne.expects(new InvokeOnceMatcher()).method("doStuff");
		mockTwo.expects(new InvokeOnceMatcher()).method("doOtherStuff");

		// show that mockOne gets Verified
		try {
			mocker.verifyMocks();
			Verify.impossible("should of thrown VerificationException");
		}  catch (VerificationException ve) {
			containsString(ve.toString(), "expected once: doStuff");
		}
	}

	private void containsString(String string, String contains) {
		Verify.that(string.indexOf(contains) >= 0);
	}
}
