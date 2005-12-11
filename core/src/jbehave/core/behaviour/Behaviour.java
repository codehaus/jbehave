package jbehave.core.behaviour;

import jbehave.core.listener.ResultListener;

/**
 * Implement this if you want to use a {@link BehaviourVerifier}
 * @author Dan North
 */
public interface Behaviour {
    /**
     * Verify the behaviour
     * 
     * For instance, for a {@link BehaviourClass} this would verify each of its
     * <tt>BehaviourMethods</tt>. For a {@link BehaviourMethod} it would verify the
     * method itself.
     * 
     * @param listener the listener to report results to
     */
    void verifyTo(ResultListener listener);
}
