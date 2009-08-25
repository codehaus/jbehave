/*
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.core.listener;

import org.jbehave.core.behaviour.Behaviour;
import org.jbehave.core.result.Result;


/**
 * A decorator of a listener to allows to check if the verification succeded or failed.
 * 
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 * @author Mauro Talevi
 */
public class ValidatingListener implements BehaviourListener {
	private boolean verificationFailed;
    private final BehaviourListener delegate;
    
    public ValidatingListener(BehaviourListener delegate) {
        this.delegate = delegate;
    }

	public boolean verificationFailed() {
		return verificationFailed;
	}

    public void after(Behaviour behaviour) {
        delegate.after(behaviour);
    }

    public void gotResult(Result result) {
        if (!result.succeeded()) {
            verificationFailed = true;
        }
        delegate.gotResult(result);
    }

    public void before(Behaviour behaviour) {
        delegate.before(behaviour);
    }

}
