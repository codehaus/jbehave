/*
 * Created on 17-Dec-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import com.thoughtworks.jbehave.core.Verify;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MockObjectBehaviour {
    public interface Foo {
        void doSomething();
    }
    
    public void shouldCreateObjectThatCanBeCastToTheCorrectType() throws Exception {
        // given...
        Mock mock = MockObject.mock(Foo.class, "foo");

        // verify...
        Verify.that(mock instanceof Foo);
    }
}
