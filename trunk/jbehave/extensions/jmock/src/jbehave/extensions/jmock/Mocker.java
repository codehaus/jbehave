package jbehave.extensions.jmock;

import org.jmock.Mock;
import jbehave.framework.exception.VerificationException;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Jul-2004
 */
public interface Mocker {
    /** Null Object for Mocker */
	Mocker NULL = new Mocker() {
        public Mock mock(Class mockedType) {
            return null;
        }
        public void verifyMocks() throws VerificationException {
        }
    };

    Mock mock(Class mockedType);

	void verifyMocks() throws VerificationException;
}
