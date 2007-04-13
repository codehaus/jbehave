package org.jbehave.core.matchers;

import org.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

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
