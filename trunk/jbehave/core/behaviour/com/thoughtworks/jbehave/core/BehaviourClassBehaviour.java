/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassBehaviour extends BehaviourSupport {
    
    public void shouldTellMethodsToAcceptVisitor() throws Exception {
        // given...
        BehaviourClass behaviourClass = new BehaviourClass(HasTwoMethods.class);
        Mock visitor = mock(Visitor.class);
        
        // expect...
        visitor.expects("visit").with(behaviourClass).willReturn("hello").id("1");
        visitor.expects("visit").with(new MatchesBehaviourMethodName("shouldDoSomething")).id("2").after("1");
        visitor.expects("visit").with(new MatchesBehaviourMethodName("shouldDoSomethingElse")).id("3").after("2");
        
        // when...
        behaviourClass.accept((Visitor) visitor);
        
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
    
    public void shouldPassClassesIntoVisitor() throws Exception {
        // given...
        Mock visitor = mock(Visitor.class);
        BehaviourClass behaviourClass = new BehaviourClass(SomeBehaviourClasses.class);
        
        // expect...
        visitor.expects("visit").with(new MatchesBehaviourClassName(SomeBehaviourClasses.class)).id("1");
        visitor.expects("visit").with(new MatchesBehaviourClassName(OneBehaviourClass.class)).id("2").after("1");
        visitor.expects("visit").with(new MatchesBehaviourClassName(AnotherBehaviourClass.class)).after("2");
        
        // when...
        behaviourClass.accept((Visitor) visitor);
        
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
}
