/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.BehaviourClassContainer;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Verifier;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.core.verifiers.DontInvokeMethod;
import com.thoughtworks.jbehave.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassBehaviour extends UsingMiniMock {
    
    public static class HasTwoMethods {
        public void shouldDoSomething() {
        }
        public void shouldDoSomethingElse() {
        }
    }
    
    class MatchesBehaviourMethod implements Constraint {
        private final String methodName;

        MatchesBehaviourMethod(String methodName) {
            this.methodName = methodName;
        }

        public boolean matches(Object arg) {
            return arg instanceof BehaviourMethod && ((BehaviourMethod)arg).methodToVerify().getName().equals(methodName);
        }
    }
    
    public void shouldTellMethodsToAcceptVisitor() throws Exception {
        // given...
        BehaviourClass behaviourClass = new BehaviourClass(HasTwoMethods.class, new DontInvokeMethod());
        Mock visitor = new Mock(Visitor.class);
        visitor.stubs("gotResult", anything());
        
        // expect...
        visitor.expects("before", behaviourClass).willReturn("hello");

        visitor.expects("before", new MatchesBehaviourMethod("shouldDoSomething"));
        visitor.expects("after", new MatchesBehaviourMethod("shouldDoSomething"));
        
        visitor.expects("before", new MatchesBehaviourMethod("shouldDoSomethingElse"));
        visitor.expects("after", new MatchesBehaviourMethod("shouldDoSomethingElse"));

        visitor.expects("after", behaviourClass);
        
        // when...
        behaviourClass.accept((Visitor) visitor.proxy());
        
        // verify...
        verifyMocks();
    }
    
    public void shouldPassVerifierIntoVisitableMethods() throws Exception {
        // given...
        Mock verifier = new Mock(Verifier.class);
        BehaviourClass behaviourClass = new BehaviourClass(HasTwoMethods.class, (Verifier) verifier.proxy());
        
        // expect...
        verifier.expects("verify", new MatchesBehaviourMethod("shouldDoSomething"));
        verifier.expects("verify", new MatchesBehaviourMethod("shouldDoSomethingElse"));
        
        // when...
        behaviourClass.accept((Visitor)stub(Visitor.class));
        
        // verify...
        verifyMocks();
    }
    
    public static class OneBehaviourClass {
    }
    public static class AnotherBehaviourClass {
    }
    public static class SomeBehaviourClasses implements BehaviourClassContainer {
        public Class[] getBehaviourClasses() {
            return new Class[] {OneBehaviourClass.class, AnotherBehaviourClass.class};
        }
    }
    
    class MatchBehaviourClass implements Constraint {
        private final Class classToMatch;
        MatchBehaviourClass(Class classToMatch) {
            this.classToMatch = classToMatch;
        }
        public boolean matches(Object arg) {
            return arg instanceof BehaviourClass && classToMatch == ((BehaviourClass)arg).classToVerify();
        }
        public String toString() {
            String name = classToMatch.getName();
            return "match " + name.substring(name.lastIndexOf('$') + 1);
        }
    }
    
    public void shouldPassClassesIntoVisitor() throws Exception {
        // given...
        Mock visitor = new Mock(Visitor.class);
        Verifier verifier = (Verifier)stub(Verifier.class);
        BehaviourClass behaviourClass = new BehaviourClass(SomeBehaviourClasses.class, verifier);
        
        // expect...
        visitor.expects("before", new MatchBehaviourClass(SomeBehaviourClasses.class));
        
        visitor.expects("before", new MatchBehaviourClass(OneBehaviourClass.class));
        visitor.expects("after", new MatchBehaviourClass(OneBehaviourClass.class));
        
        visitor.expects("before", new MatchBehaviourClass(AnotherBehaviourClass.class));
        visitor.expects("after", new MatchBehaviourClass(AnotherBehaviourClass.class));
        
        visitor.expects("after", new MatchBehaviourClass(SomeBehaviourClasses.class));
        
        // when...
        behaviourClass.accept((Visitor) visitor.proxy());
        
        // then...
        verifyMocks();
    }
    
    public static class HasOneMethod {
        public void shouldDoSomething() {}
    }
    
    public static class HasOneBehaviourClass implements BehaviourClassContainer {
        public Class[] getBehaviourClasses() {
            return new Class[] {HasOneMethod.class};
        }
    }
    
    public void shouldPassVerifierIntoBehaviourClasses() throws Exception {
        // given...
        Mock verifier = new Mock(Verifier.class);
        BehaviourClass behaviourClass = new BehaviourClass(HasOneBehaviourClass.class, (Verifier) verifier.proxy());
        
        // expect...
        verifier.expects("verify", anything());
        
        // when...
        behaviourClass.accept((Visitor)stub(Visitor.class));
        
        // then...
        verifyMocks();
    }
}
