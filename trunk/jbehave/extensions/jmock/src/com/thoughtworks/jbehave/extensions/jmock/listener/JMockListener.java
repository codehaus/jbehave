/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock.listener;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.exception.DelegatingVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListener implements BehaviourListener {

    public boolean caresAbout(Behaviour behaviour) {
        return behaviour instanceof BehaviourMethod;
    }
    
    public void behaviourVerificationStarting(Behaviour behaviour) {
    }

	public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        Object instance = ((BehaviourMethod)behaviour).getInstance();
        if (result.getCause() instanceof junit.framework.AssertionFailedError) {
            return new Result(
                    result.getName(),
                    new DelegatingVerificationException(result.getCause()));
        }
		else if (result.succeeded() && instance instanceof UsingJMock) {
            try {
                ((UsingJMock)instance).verify();
            }
            catch (VerificationException e) {
                return new Result(result.getName(), e);
            }
        }
        return result;
	}
}
