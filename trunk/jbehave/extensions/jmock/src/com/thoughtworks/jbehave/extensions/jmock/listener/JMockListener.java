/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.AssertionFailedError;

import org.jmock.Mock;

import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listeners.ListenerSupport;
import com.thoughtworks.jbehave.core.responsibility.Result;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListener extends ListenerSupport {

	public void behaviourClassVerificationStarting(Class spec) {
	}

	public void responsibilityVerificationStarting(Method responsibilityMethod) {
        UsingJMock.Mocks.clear(); // whatever
	}

	public Result responsibilityVerificationEnding(Result result, Object instance) {
		try {
			UsingJMock.Mocks.verify();
		} catch (VerificationException e) {
			return createResult(result, e);
		}

		return verifyFields(instance, result);
	}

	private Result verifyFields(Object instance, Result result) {
		Field[] fields = instance.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (Mock.class.equals(field.getType())) {
				try {
					verifyMock(field, instance);
				} catch (AssertionFailedError error) {
					return createResult(result,new VerificationException(error.getMessage()));
				}
			}
		}
		return result;
	}

	private Result createResult(Result result, VerificationException ve) {
		return new Result(result.getBehaviourClassName(), result.getName(), ve);
	}

	private void verifyMock(Field field, Object executedInstance) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
        	Mock mock = (Mock) field.get(executedInstance);
			mock.verify();
        } catch (IllegalArgumentException ignored) {
        } catch (IllegalAccessException ignored) {
        }
    }
}
