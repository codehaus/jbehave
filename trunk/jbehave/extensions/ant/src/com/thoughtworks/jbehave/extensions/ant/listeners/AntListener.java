/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant.listeners;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener implements BehaviourListener {
	private boolean failBuild;

    public void behaviourVerificationStarting(Behaviour behaviour) {
    }

	public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
		if (!result.succeeded()) {
			failBuild = true;
		}
		return result;
	}

	public boolean failBuild() {
		return failBuild;
	}

    public boolean caresAbout(Behaviour behaviour) {
        return behaviour instanceof BehaviourMethod;
    }
}
