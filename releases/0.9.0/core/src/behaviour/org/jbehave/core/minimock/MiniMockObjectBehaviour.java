/*
 * Created on 17-Dec-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock;

import org.jbehave.core.Ensure;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.mock.Mock;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockObjectBehaviour extends UsingMiniMock {
    public interface Foo {
        void doSomething();
        void doSomething(String arg);
        void doSomethingElse();
    }
    
    public void shouldCreateObjectThatCanBeCastToTheCorrectType() throws Exception {
        // given...
        Mock mock = MiniMockObject.mock(Foo.class, "foo");

        // verify...
        Ensure.that(mock instanceof Foo);
    }
    
    public void shouldCreateObjectWithInterfaceFromSystemClassLoader() throws Exception {
        // given
        Mock mock = MiniMockObject.mock(Comparable.class, "comparable");
        
        // verify
        Ensure.that(mock instanceof Comparable);
    }
    
    public void shouldSucceedWhenMethodCalledWithExpectedArgument() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        
        mock.expects("doSomething").with(eq("A"));
        
        ((Foo)mock).doSomething("A");
        
        try {
            mock.verify();
        } catch (VerificationException ve) {
            Ensure.that(false);
        }
    }
    
    public void shouldFailOnVerifyWhenMethodCalledWithExpectedThenUnexpectedArgument() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        
        mock.expects("doSomething").with(eq("A"));
        
        ((Foo)mock).doSomething("A");
        
        boolean skippedThis = true;
        
        try {
            ((Foo)mock).doSomething("B");
            skippedThis = false;
        } catch (VerificationException ve) {
            Ensure.that(ve.getMessage(), eq("Unexpected arguments for foo.doSomething" + newLine() +
                    "Expected:" + newLine() +
                    "doSomething[equal to <A>]" + newLine() +
                    "Got:" + newLine() +
                    "doSomething[B]"));
        }
        Ensure.that(skippedThis);
    }
    
    private String newLine() {
        return System.getProperty("line.separator");
    }

    public void shouldSucceedOnVerifyWhenMethodCalledWithExpectedArgumentThenOtherMethodCalled() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        mock.expects("doSomething");
        
        ((Foo)mock).doSomething();
        ((Foo)mock).doSomethingElse();
        
        mock.verify();
    }
    
    public void shouldCreateStrictMockWithDefaultName() throws Exception {
        // given
        Mock strictMock = MiniMockObject.strictMock(Foo.class, "foo");
        Exception caughtException = null;
        
        // when
        try {
            ((Foo)strictMock).doSomething();
        }
        catch (Exception e) {
            caughtException = e;
        }
        
        // then
        ensureThat(caughtException, isA(VerificationException.class));
    }
}
