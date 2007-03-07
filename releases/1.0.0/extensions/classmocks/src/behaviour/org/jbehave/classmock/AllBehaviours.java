package org.jbehave.classmock;

import org.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours{
    public Class[] getBehaviours() {
        return new Class[] {
                ClassMockObjectBehaviour.class,
                UsingClassMockBehaviour.class
        };
    }

}
