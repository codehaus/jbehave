/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock.listener;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.ResponsibilityListener;
import com.thoughtworks.jbehave.core.exception.DelegatingVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.responsibility.Result;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListener implements ResponsibilityListener {

    public void responsibilityVerificationStarting(Method responsibilityMethod) {
    }

	public Result responsibilityVerificationEnding(Result result, Object instance) {
        if (result.getCause() instanceof junit.framework.AssertionFailedError) {
            return new Result(
                    result.getBehaviourClassName(),
                    result.getName(),
                    new DelegatingVerificationException(result.getCause()));
        }
		else if (result.succeeded() && instance instanceof UsingJMock) {
            try {
                ((UsingJMock)instance).verify();
            }
            catch (VerificationException e) {
                return new Result(result.getBehaviourClassName(), result.getName(), e);
            }
        }
        return result;
	}
}
