package org.jbehave.core.matchers;

import org.jbehave.core.behaviour.Behaviours;

/**
 * @author Mauro Talevi
 */
public class MatchersBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                UsingCollectionMatchersBehaviour.class,
                UsingEqualityMatchersBehaviour.class,
                UsingLogicalMatchersBehaviour.class,
                UsingStringMatchersBehaviour.class,
                UsingExceptionsBehaviour.class
        };
    }

}
