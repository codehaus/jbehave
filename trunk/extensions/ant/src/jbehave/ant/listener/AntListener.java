/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant.listener;

import jbehave.core.behaviour.Behaviour;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.result.Result;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener implements BehaviourListener {
	private boolean verificationFailed;
    private final BehaviourListener delegate;
    
    public AntListener(BehaviourListener delegate) {
        this.delegate = delegate;
    }

	public boolean verificationFailed() {
		return verificationFailed;
	}

    public void gotResult(Result result) {
        if (!result.succeeded()) {
            verificationFailed = true;
        }
        delegate.gotResult(result);
    }

    public void before(Behaviour behaviour) {
        // TODO Auto-generated method stub
        
    }

    public void after(Behaviour behaviour) {
        // TODO Auto-generated method stub
        
    }
}
