/*
 * Created on 25-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import jbehave.core.Ensure;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodVerifierBehaviour extends BehaviourSupport {
    
	public static class StoresInvocation {
		public boolean wasInvoked = false;
		public void shouldDoSomething() {
			wasInvoked = true;
		}
	}
	
    public void shouldInvokeBehaviourMethod() throws Exception {
        // given...
        BehaviourMethodVerifier verifier = new BehaviourMethodVerifier();
		StoresInvocation instance = new StoresInvocation();
		BehaviourMethod behaviourMethod = new BehaviourMethod(instance, "shouldDoSomething");
        
        // when...
        behaviourMethod.accept(verifier);
        
        // verify...
		Ensure.that(instance.wasInvoked);
    }
}
