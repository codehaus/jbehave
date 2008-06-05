package org.jbehave.core.exception;

import org.jbehave.core.behaviour.Behaviours;

/**
 * @author Mauro Talevi
 */
public class ExceptionBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                JBehaveFrameworkErrorBehaviour.class
        };
    }

}
