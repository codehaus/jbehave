/*
 * Created on 20-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import org.jmock.cglib.CGLIBCoreMock;
import org.jmock.core.DynamicMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingJMockWithCGLIB extends UsingJMock {
    protected class Mock extends UsingJMock.Mock {
        public Mock(Class mockedType) {
            this( new CGLIBCoreMock(mockedType) );
        }
        
        public Mock(Class mockedType, String name) {
            this( new CGLIBCoreMock(mockedType,name) );
        }
        
        public Mock(DynamicMock coreMock) {
            super(coreMock);
        }
    }
}
