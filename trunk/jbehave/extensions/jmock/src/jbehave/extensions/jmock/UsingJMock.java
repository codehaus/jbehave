/*
 * Created on 09-Aug-2004
 *
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jbehave.framework.exception.VerificationException;
import junit.framework.AssertionFailedError;

import org.jmock.core.DynamicMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface UsingJMock extends JMockMixins {
    /**
     * Convenience methods for managing mocks
     *
     * The pixies will ensure that
     * the {@link Mock} gets verified when the Responsibility method ends.
     */
    class Mocks {
        private static final Set mocks = new HashSet();

        public static void clear() {
            mocks.clear();
        }

        private static void add(Mock mock) {
            mocks.add(mock);
        }

        public static void verify() {
            for (Iterator i = mocks.iterator(); i.hasNext();) {
                Mock mock = null;
                try {
                    mock = (Mock) i.next();
                    mock.verify();
                } catch (AssertionFailedError e) {
                    VerificationException fakedException = new VerificationException(e.getMessage());
                    mock.fakeExceptionStackTrace(fakedException);
                    throw fakedException;
                }
            }
        }
    }

    /**
     * Interceptor class for creating instances of {@link org.jmock.Mock}.
     *
     * On construction, capture a stack trace of the point of construction, and
     * add the new instance to a {@link Set} of <tt>Mock</tt> instances to be
     * verified later.
     */
    class Mock extends org.jmock.Mock {
        private final Exception exceptionFromCreationPoint;

        public Mock(Class mockedType) {
            super(mockedType);
            exceptionFromCreationPoint = new Exception();
            Mocks.add(this);
        }

        public Mock(Class mockedType, String name) {
            super(mockedType, name);
            exceptionFromCreationPoint = new Exception();
            Mocks.add(this);
        }

        public Mock(DynamicMock coreMock) {
            super(coreMock);
            exceptionFromCreationPoint = new Exception();
            Mocks.add(this);
        }

        /**
         * Set up a fake stack trace pointing back to the point where the
         * mock was constructed, rather than the obscure part of the framework
         * that actually does the mocking.
         */
        public void fakeExceptionStackTrace(Exception exceptionToFake) {
            try {
                StackTraceElement[] creationStackTrace = exceptionFromCreationPoint.getStackTrace();
                StackTraceElement[] fakedStackTrace =
                    new StackTraceElement[creationStackTrace.length - 1];
                System.arraycopy(creationStackTrace, 1, fakedStackTrace, 0, fakedStackTrace.length);
                exceptionToFake.setStackTrace(fakedStackTrace);
//                exceptionToFake.printStackTrace();
            } catch (NoSuchMethodError e) {
                // shame - not running in a 1.4 VM
            }
        }
    }
}
