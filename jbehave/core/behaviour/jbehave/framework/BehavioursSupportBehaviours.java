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

/**
 * Test the {@link BehavioursSupport} class
 *
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class BehavioursSupportBehaviours {

    public static class BehaviourClassWithSingleBehaviour {
        public void shouldBehaveInSomeWay() {
        }
    }

    private void verifyContainsBehaviourName(String name, Collection behaviours) {
        for (Iterator i = behaviours.iterator(); i.hasNext();) {
            Criterion behaviour = (Criterion)i.next();
            if (behaviour.getName().equals(name)) return;
        }
        Verify.impossible(name + " not found in behaviours");
    }

    public void shouldRecogniseSingleBehaviour() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(BehaviourClassWithSingleBehaviour.class);
        Verify.equal(1, behaviours.size());
        verifyContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }

    public static class BehaviourClassWithTwoBehaviours {
        public void shouldDoOneThing() {
        }

        public void shouldDoAnotherThing() {
        }
    }

    public void shouldRecogniseTwoBehaviours() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(BehaviourClassWithTwoBehaviours.class);

        Verify.equal(2, behaviours.size());
        verifyContainsBehaviourName("shouldDoOneThing", behaviours);
        verifyContainsBehaviourName("shouldDoAnotherThing", behaviours);
    }

    public static class BehaviourClassWithNonPublicMethods {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }

        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }
    }

    public void shouldIgnoreNonPublicBehaviourMethods() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(BehaviourClassWithNonPublicMethods.class);
        Verify.equal(0, behaviours.size());
    }

    public static class BehaviourClassWithBehaviourAndNonPublicMethods {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }

        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }

        public void shouldBehaveInSomeWay() {
        }
    }

    public void shouldIgnoreNonPublicBehaviourMethodsMixedWithBehaviours() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(BehaviourClassWithBehaviourAndNonPublicMethods.class);
        Verify.equal(1, behaviours.size());
        verifyContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }

    public static class BehaviourSuperclassWithBehaviour {
        public void shouldDoSomething() {
        }
    }

    public static class BehaviourSubclassInheritingBehaviourMethod extends BehaviourSuperclassWithBehaviour {
    }

    public void shouldRecogniseBehaviourInheritedFromSuperclass() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(BehaviourSubclassInheritingBehaviourMethod.class);
        Verify.equal(1, behaviours.size());
        verifyContainsBehaviourName("shouldDoSomething", behaviours);
    }

    public static class AggregateBehaviourClass implements Aggregate {
        public Class[] getBehaviourClasses() {
            return new Class[] { BehaviourClassWithSingleBehaviour.class };
        }
    }

    public void shouldFindMethodsInAggregatedBehaviourClass() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(AggregateBehaviourClass.class);
        Verify.equal(1, behaviours.size());
        verifyContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }

    public static class NestedAggregateBehaviourClass implements Aggregate {
        public Class[] getBehaviourClasses() {
            return new Class[] { AggregateBehaviourClass.class };
        }
    }

    public void shouldFindMethodsInNestedAggregatedBehaviourClass() throws Exception {
        Collection behaviours = BehavioursSupport.getCriteria(NestedAggregateBehaviourClass.class);
        Verify.equal(1, behaviours.size());
        verifyContainsBehaviourName("shouldBehaveInSomeWay", behaviours);
    }
    
    public void shouldFindMethodsThatStartWithWill() throws Exception {
		
	}
}
