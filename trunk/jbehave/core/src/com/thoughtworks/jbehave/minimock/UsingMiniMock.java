/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.minimock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingMiniMock extends MiniMockBase {
    
    private final List mocks = new ArrayList();
    
    protected Mock mock(Class type) {
        return mock(type, "MockObject " + type.getName());
    }
    
    protected Mock mock(final Class type, final String name) {
        Mock mock = MockObject.mock(type, name);
        mocks.add(mock);
        return mock;
    }

    /** Verify all registered mocks */
    protected void verifyMocks() {
        for (Iterator i = mocks.iterator(); i.hasNext();) {
            ((Mock) i.next()).verify();
        }
    }

    /** stub an interface */
    protected Object stub(final Class type) {
        return Stub.instance(type);
    }
}
