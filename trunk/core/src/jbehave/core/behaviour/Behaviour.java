package jbehave.core.behaviour;

import jbehave.core.listener.BehaviourListener;

/**
 * Implement this if you want to use a {@link BehaviourVerifier}
 * @author Dan North
 */
public interface Behaviour {
    /**
     * Verify the behaviour
     * 
     * Any implementing class is responsible for notifying the <tt>BehaviourListener</tt>
     * {@link BehaviourListener#before(Behaviour) before} and
     * {@link BehaviourListener#after(Behaviour) after} verifying the behaviour
     * 
     * For instance, for a {@link BehaviourClass} this would verify each of its
     * <tt>BehaviourMethods</tt>. For a {@link BehaviourMethod} it would verify the
     * method itself.
     * 
     * @param listener the listener to report results to
     */
    void verifyTo(BehaviourListener listener);

    /**
     * Count the number of behaviours to verify
     * 
     * For a {@link BehaviourMethod} this would be exactly 1. For a {@link BehaviourClass}
     * it would be the count of behaviour methods (including those in nested behaviour classes).
     * 
     * @return
     */
    int countBehaviours();
}
