package com.thoughtworks.jbehave.extensions.jmock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.jmock.cglib.CGLIBCoreMock;
import org.jmock.core.CoreMock;
import org.jmock.core.DynamicMock;
import org.jmock.core.Verifiable;
import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.minimock.UsingMocks;

/**
 * @author <a href="mailto:dnorth@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 */
public abstract class UsingJMock extends JMockSugar implements UsingMocks {

    protected final List mocks = new ArrayList();
    
    public void verifyMocks() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            try {
                ((Verifiable) i.next()).verify();
            }
            catch (Error e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    private void addMock(Mock m) {
        mocks.add(m);
    }
    
    public List getMocks() {
        return mocks;
    }

    public boolean containsMocks() {
        return !mocks.isEmpty();
    }
    
    /**
     * Interceptor class for creating instances of {@link org.jmock.Mock}.
     *
     * On construction, capture a stack trace of the point of construction, and
     * add the new instance to a {@link Set} of <tt>Mock</tt> instances to be
     * verified later.
     */
    protected class Mock extends org.jmock.Mock {
        private final Exception exceptionFromCreationPoint;

        public Mock(DynamicMock coreMock) {
            super(coreMock);
            exceptionFromCreationPoint = new Exception();
            addMock(this);
        }
        
        public Mock(Class mockedType, String name) {
            this(dynamicMockFor(mockedType, name));
        }

        public Mock(Class mockedType) {
            this(mockedType, CoreMock.mockNameFromClass(mockedType));
        }

        
        public void verify() {
            try {
                super.verify();
            }
            catch (AssertionFailedError e) {
                throw new NestedVerificationException(e.getMessage(), exceptionFromCreationPoint);
            }
        }

        public void stubsEverythingElse() {
            setDefaultStub(new DefaultResultStub());
        }
    }
    
    /**
     * Create the lightest-weight DynamicMock we can for a particular type.
     * 
     * We only use CGLIB if we have to.
     */
    private static DynamicMock dynamicMockFor(Class type, String name) {
        if (type.isInterface()) {
            return new CoreMock(type, name);
        }
        else {
            return new CGLIBCoreMock(type, name);
        }
    }
    
    // Syntactic sugar methods that use our Mock class
    
    protected Object stub(Class type) {
        Mock mock = new Mock(type);
        mock.setDefaultStub(new DefaultResultStub());
        return mock.proxy();
    }
    
    protected Object stub(Class type, String name) {
        Mock mock = new Mock(type, name);
        mock.setDefaultStub(new DefaultResultStub());
        return mock.proxy();
    }
}
