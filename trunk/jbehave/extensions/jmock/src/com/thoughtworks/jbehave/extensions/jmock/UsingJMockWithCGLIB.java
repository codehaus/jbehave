/*
 * Created on 20-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import org.jmock.cglib.CGLIBCoreMock;
import org.jmock.core.CoreMock;
import org.jmock.core.DynamicMock;

/**
 * Support class that plays nice with CGLIB.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingJMockWithCGLIB extends UsingJMock {
    /**
     * Does The Right Thing with creating a mock.
     * 
     * If the type to be mocked is an interface it uses a regular {@link org.jmock.core.CoreMock}. Otherwise if
     * the type is an abstract or concrete class, it creates a {@link org.jmock.cglib.CGLIBCoreMock} instead.
     */
    protected class Mock extends UsingJMock.Mock {
        public Mock(DynamicMock coreMock) {
            super(coreMock);
        }
        
        public Mock(Class mockedType, String name) {
            this(dynamicMockFor(mockedType, name));
        }

        public Mock(Class mockedType) {
            this(mockedType, CoreMock.mockNameFromClass(mockedType));
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
}
