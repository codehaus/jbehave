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
 * Test the {@link CriteriaExtractor} class
 *
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CriteriaSupportSpec {

    public static class SpecWithSingleCriteria {
        public void shouldBehaveInSomeWay() {
        }
    }

    private void verifyContainsCriteriaName(String name, Collection verifiers) {
        for (Iterator i = verifiers.iterator(); i.hasNext();) {
            CriteriaVerifier verifier = (CriteriaVerifier)i.next();
            if (verifier.getName().equals(name)) return;
        }
        Verify.impossible(name + " not found in criteria");
    }

    public void shouldRecogniseSingleCriteria() throws Exception {
        Collection verifiers = getCriteriaVerifiers(SpecWithSingleCriteria.class);
        Verify.equal(1, verifiers.size());
        verifyContainsCriteriaName("shouldBehaveInSomeWay", verifiers);
    }

    public static class SpecWithTwoCriteria {
        public void shouldDoOneThing() {
        }

        public void shouldDoAnotherThing() {
        }
    }

    public void shouldRecogniseTwoCriteria() throws Exception {
        Collection verifiers = getCriteriaVerifiers(SpecWithTwoCriteria.class);

        Verify.equal(2, verifiers.size());
        verifyContainsCriteriaName("shouldDoOneThing", verifiers);
        verifyContainsCriteriaName("shouldDoAnotherThing", verifiers);
    }

    public static class SpecWithNonPublicCriteria {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }

        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }
    }

    public void shouldIgnoreNonPublicCriteria() throws Exception {
        Collection behaviours = getCriteriaVerifiers(SpecWithNonPublicCriteria.class);
        Verify.equal(0, behaviours.size());
    }

    public static class BehaviourClassWithPublicAndNonPublicCriteria {
        private void shouldDoSomethingWhichWillBeIgnored() {
        }

        protected void shouldDoSomethingElseWhichWillBeIgnored() {
        }

        public void shouldBehaveInSomeWay() {
        }
    }

    public void shouldIgnoreNonPublicCriteriaMixedWithPublicCriteria() throws Exception {
        Collection verifiers = getCriteriaVerifiers(BehaviourClassWithPublicAndNonPublicCriteria.class);
        Verify.equal(1, verifiers.size());
        verifyContainsCriteriaName("shouldBehaveInSomeWay", verifiers);
    }

    private Collection getCriteriaVerifiers(Class spec) {
        return new CriteriaExtractor(spec).extractCriteria();
    }

    public static class SpecSuperclassWithCriteria {
        public void shouldDoSomething() {
        }
    }

    public static class SpecSubclassInheritingCriteria extends SpecSuperclassWithCriteria {
    }

    public void shouldRecogniseCriteriaInheritedFromSuperclass() throws Exception {
        Collection verifiers = getCriteriaVerifiers(SpecSubclassInheritingCriteria.class);
        Verify.equal(1, verifiers.size());
        verifyContainsCriteriaName("shouldDoSomething", verifiers);
    }

    public static class AggregateSpec implements Aggregate {
        public Class[] getSpecs() {
            return new Class[] { SpecWithSingleCriteria.class };
        }
    }

    public void shouldFindCriteriasInAggregatedSpec() throws Exception {
        Collection verifiers = getCriteriaVerifiers(AggregateSpec.class);
        Verify.equal(1, verifiers.size());
        verifyContainsCriteriaName("shouldBehaveInSomeWay", verifiers);
    }

    public static class NestedAggregateSpec implements Aggregate {
        public Class[] getSpecs() {
            return new Class[] { AggregateSpec.class };
        }
    }

    public void shouldFindCriteriaInNestedAggregatedSpec() throws Exception {
        Collection verifiers = getCriteriaVerifiers(NestedAggregateSpec.class);
        Verify.equal(1, verifiers.size());
        verifyContainsCriteriaName("shouldBehaveInSomeWay", verifiers);
    }
    
    public void shouldFindMethodsThatStartWithWill() throws Exception {
		System.out.println("TODO: shouldFindMethodsThatStartWithWill");
	}
}
