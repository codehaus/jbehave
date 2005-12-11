/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import jbehave.core.listener.ResultListener;
import jbehave.core.minimock.Constraint;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.result.Result;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassBehaviour extends UsingMiniMock {
    
    public static class ClassWithOneBehaviourMethod {
        public void shouldDoOneThing() {}
    }
    
    private Constraint successfulResultFromMethodCalled(final String name) {
        return new Constraint() {
            public boolean matches(Object arg) {
                Result result = (Result)arg;
                return result.succeeded() && result.name().equals(name);
            }
            public String toString() {
                return "successful result from method called " + name;
            }
        };
    }
    
    public void shouldVerifySingleBehaviourMethod() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(ClassWithOneBehaviourMethod.class);

        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoOneThing"));
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class ClassWithTwoBehaviourMethods {
        public void shouldDoOneThing() {}
        public void shouldDoAnotherThing() {}
    }
    
    public void shouldVerifyTwoBehaviourMethods() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(ClassWithTwoBehaviourMethods.class);
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoOneThing"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoAnotherThing"));
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class NestedBehaviourClass1 {
        public void shouldDoSomething1() {}
    }
    public static class NestedBehaviourClass2 {
        public void shouldDoSomething2() {}
    }
    public static class ContainsNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {NestedBehaviourClass1.class, NestedBehaviourClass2.class};
        }
    }
    
    public void shouldVerifyNestedBehaviourClasses() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(ContainsNestedClasses.class);
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class ContainsDeeplyNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {ContainsNestedClasses.class};
        }
    }
    
    public void shouldVerifyDeeplyNestedBehaviourClasses() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(ContainsDeeplyNestedClasses.class);
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class HasNoBehaviourMethods {
        public void dontRunMe() {}
    }
    
    public void shouldIgnorePublicMethodsThatDontStartWithShould() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(HasNoBehaviourMethods.class);
        
        // expect...
        listener.expects("gotResult").never();
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class HasNonPublicBehaviourMethod {
        protected void shouldNotRunMe() {}
    }
    
    public void shouldIgnoreNonPublicMethodsThatStartWithShould() throws Exception {
        // given...
        Mock listener = mock(ResultListener.class);
        Behaviour behaviour = new BehaviourClass(HasNonPublicBehaviourMethod.class);
        
        // expect...
        listener.expects("gotResult").never();
        
        // when...
        behaviour.verifyTo((ResultListener) listener);
        
        // then...
        verifyMocks();
    }
}
