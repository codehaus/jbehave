package org.jbehave.core.mock;

import org.jbehave.core.behaviour.Behaviours;

/**
 * @author Mauro Talevi
 */
public class MockBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
               ExpectationBehaviour.class,
               UsingMatchersBehaviour.class
        };
    }

}
