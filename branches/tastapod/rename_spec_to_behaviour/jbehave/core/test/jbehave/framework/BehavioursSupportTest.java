/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.framework;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Test the {@link BehaviourGroupSupport} class
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehavioursSupportTest extends TestCase {

    public static class BehaviourClassWithSingleBehaviour {
        public void shouldBehaveInSomeWay() {
        }
    }
    
    private void assertContainsBehaviourName(String name, Collection behaviours) {
        for (Iterator i = behaviours.iterator(); i.hasNext();) {
            Behaviour behaviour = (Behaviour)i.next();
            if (behaviour.getName().equals(name)) return;
        }
        fail(name + " not found in behaviours");
    }

    public void testShouldRecogniseSingleBehaviour() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(BehaviourClassWithSingleBehaviour.class);
        assertEquals(1, behaviours.size());
        assertContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }
    
    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoOneThing() {
        }
        
        public void shouldDoAnotherThing() {
        }
    }
    
    public void testShouldRecogniseTwoBehaviours() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(BehaviourClassWithTwoBehaviours.class);
        
        // the Java reflection API doesn't guarantee the order of methods - shame
        assertEquals(2, behaviours.size());
        assertContainsBehaviourName("shouldDoOneThing", behaviours);
        assertContainsBehaviourName("shouldDoAnotherThing", behaviours);
    }
    
    public static class BehaviourClassWithNonPublicMethods {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }
        
        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }
    }
    
    public void testShouldIgnoreNonPublicBehaviourMethods() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(BehaviourClassWithNonPublicMethods.class);
        assertEquals(0, behaviours.size());
    }

    public static class BehaviourClassWithBehaviourAndNonPublicMethods {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }
        
        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }
        
        public void shouldBehaveInSomeWay() {
        }
    }
    
    public void testShouldIgnoreNonPublicBehaviourMethodsMixedWithBehaviours() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(BehaviourClassWithBehaviourAndNonPublicMethods.class);
        assertEquals(1, behaviours.size());
        assertContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }
    
    public static class BehaviourSuperclassWithBehaviour {
        public void shouldDoSomething() {
        }
    }
    
    public static class BehaviourSubclassInheritingBehaviourMethod extends BehaviourSuperclassWithBehaviour {
    }
    
    public void testShouldRecogniseBehaviourInheritedFromSuperclass() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(BehaviourSubclassInheritingBehaviourMethod.class);
        assertEquals(1, behaviours.size());
        assertContainsBehaviourName("shouldDoSomething", behaviours);
    }
    
    public static class AggregateBehaviourClass implements Aggregate {
        public Class[] getBehaviourClasses() {
            return new Class[] { BehaviourClassWithSingleBehaviour.class };
        }
    }
    
    public void testShouldFindMethodsInAggregatedBehaviourClass() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(AggregateBehaviourClass.class);
        assertEquals(1, behaviours.size());
        assertContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }
    
    public static class NestedAggregateBehaviourClass implements Aggregate {
        public Class[] getBehaviourClasses() {
            return new Class[] { AggregateBehaviourClass.class };
        }
    }
    
    public void testShouldFindMethodsInNestedAggregatedBehaviourClass() throws Exception {
        Collection behaviours = BehavioursSupport.getBehaviours(NestedAggregateBehaviourClass.class);
        assertEquals(1, behaviours.size());
        assertContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }
}
