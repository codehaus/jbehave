/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.behaviour;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.result.Result;




/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassBehaviour extends UsingMiniMock {
    
    public static class ClassWithOneBehaviourMethod {
        public void shouldDoOneThing() {}
    }
    
    private Matcher successfulResultFromMethodCalled(final String name) {
        return new Matcher() {
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
        // given
        Mock listener = mock(BehaviourListener.class);
        listener.expects("before").once();
        listener.expects("after").once();
        listener.expects("gotResult").once();

        BehaviourClass behaviour = new BehaviourClass(ClassWithOneBehaviourMethod.class);

        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        listener.verify();
    }
    
    public void shouldCountSingleBehaviourMethod() throws Exception {
        // given
        Behaviour behaviour = new BehaviourClass(ClassWithOneBehaviourMethod.class);

        // then
        ensureThat(behaviour.countBehaviours(), eq(1));
    }
    
    public static class ClassWithTwoBehaviourMethods {
        public void shouldDoOneThing() {}
        public void shouldDoAnotherThing() {}
    }
    
    public void shouldVerifyMultipleBehaviourMethods() throws Exception {
        // given
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithTwoBehaviourMethods.class);
        
        // expect
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoOneThing"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoAnotherThing"));
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public void shouldCountMultipleBehaviourMethods() throws Exception {
        // given
        Behaviour behaviour = new BehaviourClass(ClassWithTwoBehaviourMethods.class);

        // then
        ensureThat(behaviour.countBehaviours(), eq(2));
    }

    public void shouldOnlyRunTheOnesSpecified() {
        BehaviourClass behaviour = new BehaviourClass(ClassWithTwoBehaviourMethods.class, "shouldDoOneThing");
        ensureThat(behaviour.countBehaviours(), eq(1));
    }
    
    public static class NestedBehaviourClass1 {
        public void shouldDoSomething1() {}
    }
    public static class NestedBehaviourClass2 {
        public void shouldDoSomething2() {}
    }
    public static class ClassWithNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {NestedBehaviourClass1.class, NestedBehaviourClass2.class};
        }
    }
    
    public void shouldVerifyNestedBehaviourClasses() throws Exception {
        // given
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithNestedClasses.class);
        
        // expect
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public void shouldCountNestedBehaviourClasses() throws Exception {
        // given
        Behaviour behaviour = new BehaviourClass(ClassWithNestedClasses.class);

        // when
        int count = behaviour.countBehaviours();
        
        // then
        ensureThat(count, eq(2));
    }
    
    public static class ClassWithDeeplyNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {ClassWithNestedClasses.class};
        }
    }
    
    public void shouldVerifyDeeplyNestedBehaviourClasses() throws Exception {
        // given
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithDeeplyNestedClasses.class);
        
        // expect
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public void shouldCountDeeplyNestedBehaviourMethods() throws Exception {
        // given
        Behaviour behaviour = new BehaviourClass(ClassWithDeeplyNestedClasses.class);

        // when
        int count = behaviour.countBehaviours();
        
        // then
        ensureThat(count, eq(2));
    }
    
    public static class HasNoBehaviourMethods {
        public void dontRunMe() {}
    }
    
    public void shouldIgnorePublicMethodsThatDontStartWithShould() throws Exception {
        // given
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(HasNoBehaviourMethods.class);
        
        // expect
        listener.expects("gotResult").never();
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    public static class HasNonPublicBehaviourMethod {
        protected void shouldNotRunMe() {}
    }
    
    public void shouldIgnoreNonPublicMethodsThatStartWithShould() throws Exception {
        // given
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(HasNonPublicBehaviourMethod.class);
        
        // expect
        listener.expects("gotResult").never();
        
        // when
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then
        verifyMocks();
    }
    
    private static List<CapturesClassInstance> instances;
    
    public static class CapturesClassInstance {
        public void shouldCaptureInstance() {
            instances.add(this);
        }
        public void shouldAlsoCaptureInstance() {
            instances.add(this);
        }
    }
    
    public void shouldCreateNewInstanceForEachBehaviourMethod() throws Exception {
        // given
        instances = new ArrayList<CapturesClassInstance>();
        BehaviourListener nullListener = (BehaviourListener) stub(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(CapturesClassInstance.class);
        
        // when
        behaviour.verifyTo(nullListener);
        
        // then
        ensureThat(instances.size(), eq(2));
        ensureThat(instances.get(0), not(sameInstanceAs(instances.get(1))));
    }
}
